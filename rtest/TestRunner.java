import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Compile regression tests with different compilers.
 *
 * <p>Each compiler version is specified by a classpath (an ExtendJ jar file and/or
 * compiled class directories), or by the literal {@code javac} to use the system Java
 * compiler of the running JDK.
 *
 * <p>This tool has two modes.  The diff mode compiles every test under the given test
 * root with two compiler versions and reports the tests where the versions disagree on
 * whether compilation succeeds:
 * <pre>
 *   javac TestRunner.java
 *   java TestRunner &lt;[label=]spec1&gt; &lt;[label=]spec2&gt; [testsRoot]
 * </pre>
 * The optional label prefix names the compiler versions in the report. It defaults to
 * "baseline" for the first version and "new" for the second. The tests root defaults to
 * "tests". For example, comparing the current build against a baseline jar:
 * <pre>
 *   java TestRunner baseline.jar ../java8/extendj.jar
 * </pre>
 * A test is considered to compile successfully if the compiler exits with status zero
 * and produces no output to stderr/stdout.
 *
 * <p>The worker mode runs compile jobs parsed from standard input:
 * <pre>
 *   java TestRunner --worker &lt;type&gt; &lt;classpath&gt; [&lt;type&gt; &lt;classpath&gt; ...]
 * </pre>
 * Each compiler version is given as a type ({@code extendj} or {@code javac}) followed
 * by its classpath, and is referred to by its index in that list.  The protocol is
 * line-based UTF-8 text.  The worker starts by writing a {@code LOADFAIL <index>
 * <message>} line for each compiler version that could not be loaded, followed by
 * {@code READY <count>}.  It then reads compile jobs:
 * <pre>
 *   &lt;id&gt; &lt;compilerIndex&gt; &lt;argumentCount&gt;
 *   &lt;argument&gt;    (repeated argumentCount times, one per line)
 * </pre>
 * and answers each job, in order, with:
 * <pre>
 *   RESULT &lt;id&gt; &lt;exitCode&gt; &lt;lineCount&gt;
 *   &lt;output line&gt;    (repeated lineCount times)
 * </pre>
 * The exit code is the compiler exit status: zero on success, one for a compile error,
 * and other values for configuration or internal errors.  Compiler diagnostics are
 * captured and reported as the output lines instead of being written to this tool's
 * standard output.  A {@code QUIT} line makes the worker exit.
 */
public class TestRunner {

  /** Exit code reported when the compiler could not be run at all. */
  static final int EXIT_INTERNAL_ERROR = 3;

  /** A compiler version that compiles in this JVM. */
  abstract static class Compiler {
    /** Run the compiler. Output is captured by the caller. */
    abstract int run(String[] args) throws Exception;
  }

  /** An ExtendJ build loaded from a classpath. */
  static class ExtendJ extends Compiler {
    /** Compiler entry point class names, in the order they are looked for. */
    static final String[] CLASS_NAMES = {
      "org.extendj.JavaCompiler",
      "org.jastadd.extendj.JavaCompiler",
      "org.jastadd.jastaddj.JavaCompiler",
    };

    private final Constructor<?> constructor;
    private final Method runMethod;
    private final Method compileMethod;

    ExtendJ(String classpath) throws Exception {
      Class<?> compiler = loadCompilerClass(classpath);
      Method run = null;
      Constructor<?> ctor = null;
      try {
        run = compiler.getMethod("run", String[].class);
        ctor = compiler.getConstructor();
      } catch (NoSuchMethodException e) {
        // Older ExtendJ versions only have the static compile method.
      }
      if (run != null && run.getReturnType() == int.class) {
        constructor = ctor;
        runMethod = run;
        compileMethod = null;
      } else {
        constructor = null;
        runMethod = null;
        compileMethod = compiler.getMethod("compile", String[].class);
      }
    }

    /** Load the compiler entry point class from a classpath of jars/class directories. */
    private static Class<?> loadCompilerClass(String classpath) throws Exception {
      String[] paths = classpath.split(File.pathSeparator);
      URL[] urls = new URL[paths.length];
      for (int i = 0; i < paths.length; ++i) {
        File file = new File(paths[i]);
        if (!file.exists()) {
          throw new IllegalArgumentException("no such file or directory: " + paths[i]);
        }
        urls[i] = file.toURI().toURL();
      }
      URLClassLoader classLoader =
          new URLClassLoader(urls, TestRunner.class.getClassLoader().getParent());
      Exception failure = null;
      for (String name : CLASS_NAMES) {
        try {
          return Class.forName(name, true, classLoader);
        } catch (ClassNotFoundException e) {
          failure = e;
        }
      }
      throw new IllegalArgumentException(
          "no compiler entry point in " + classpath + " (tried " + CLASS_NAMES[0] + ")", failure);
    }

    @Override
    int run(String[] args) throws Exception {
      if (runMethod != null) {
        return (Integer) runMethod.invoke(constructor.newInstance(), (Object) args);
      }
      return ((Boolean) compileMethod.invoke(null, (Object) args)) ? 0 : 1;
    }
  }

  /** The system Java compiler of the JDK running this tool. */
  static class Javac extends Compiler {
    private final javax.tools.JavaCompiler javac;

    Javac() {
      javac = javax.tools.ToolProvider.getSystemJavaCompiler();
      if (javac == null) {
        throw new IllegalStateException(
            "no system Java compiler available - run this tool with a JDK, "
            + "or configure the path to a javac executable");
      }
    }

    @Override
    int run(String[] args) {
      // Null streams make javac use System.out/System.err, which the caller captures.
      return javac.run(null, null, null, args);
    }
  }

  /** The exit status and captured diagnostics of a single compilation. */
  static class Result {
    final int exitCode;
    final String output;

    Result(int exitCode, String output) {
      this.exitCode = exitCode;
      this.output = output;
    }

    /** A compilation passes if the compiler succeeded without printing anything. */
    boolean pass() {
      return exitCode == 0 && output.isEmpty();
    }
  }

  /** Create a compiler version of the given type. */
  static Compiler create(String type, String classpath) throws Exception {
    if (type.equals("javac")) {
      return new Javac();
    } else if (type.equals("extendj")) {
      return new ExtendJ(classpath);
    }
    throw new IllegalArgumentException("unknown compiler type: " + type);
  }

  /** Compile with output captured, so that concurrent JVM output does not interfere. */
  static Result compile(Compiler compiler, String[] args) {
    PrintStream stdout = System.out;
    PrintStream stderr = System.err;
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    PrintStream capture;
    try {
      capture = new PrintStream(buf, true, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      capture = new PrintStream(buf, true);
    }
    System.setOut(capture);
    System.setErr(capture);
    int exitCode;
    try {
      exitCode = compiler.run(args);
    } catch (Throwable t) {
      t.printStackTrace(capture);
      exitCode = EXIT_INTERNAL_ERROR;
    } finally {
      capture.flush();
      System.setOut(stdout);
      System.setErr(stderr);
    }
    String output;
    try {
      output = buf.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      output = buf.toString();
    }
    return new Result(exitCode, output);
  }

  /** Compile all Java source files in a test directory. */
  static Result compileTest(Compiler compiler, File dir, String outputDir) {
    File[] sources = dir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File d, String name) {
        return name.endsWith(".java");
      }
    });
    List<String> argv = new ArrayList<String>();
    argv.add("-classpath");
    argv.add(dir.getAbsolutePath());
    if (outputDir != null) {
      argv.add("-d");
      argv.add(outputDir);
    }
    for (File source : sources) {
      argv.add(source.getAbsolutePath());
    }
    return compile(compiler, argv.toArray(new String[0]));
  }

  /** Find all test directories, i.e. directories containing Java source files. */
  static List<File> findTests(String root) throws IOException {
    final List<File> dirs = new ArrayList<File>();
    Files.walk(Paths.get(root))
        .filter(p -> p.toString().endsWith(".java"))
        .map(p -> p.getParent().toFile())
        .distinct()
        .forEach(dirs::add);
    Collections.sort(dirs);
    return dirs;
  }

  /** Split the label/spec argument. */
  static String[] splitLabel(String arg, String defaultLabel) {
    int eq = arg.indexOf('=');
    if (eq < 0) {
      return new String[] { defaultLabel, arg };
    }
    return new String[] { arg.substring(0, eq), arg.substring(eq + 1) };
  }

  /** Create the compiler version named by a {@code [label=]spec} argument. */
  static Compiler create(String spec) throws Exception {
    return create(spec.equals("javac") ? "javac" : "extendj", spec);
  }

  static void printVersion(String label, Compiler compiler) {
    System.out.format("%s: %s", label, compile(compiler, new String[] { "-version" }).output);
  }

  /** Compile all tests with two compiler versions and report where they disagree. */
  static void diff(String[] args) throws Exception {
    String[] buildA = splitLabel(args[0], "baseline");
    String[] buildB = splitLabel(args[1], "new");
    String labelA = buildA[0];
    String labelB = buildB[0];
    String root = args.length > 2 ? args[2] : "tests";
    Compiler compilerA = create(buildA[1]);
    Compiler compilerB = create(buildB[1]);

    List<File> dirs = findTests(root);
    System.out.format("Found %d tests under %s%n", dirs.size(), Paths.get(root).toAbsolutePath());
    printVersion(labelA, compilerA);
    printVersion(labelB, compilerB);

    int changed = 0;
    for (File dir : dirs) {
      boolean passA = compileTest(compilerA, dir, null).pass();
      boolean passB = compileTest(compilerB, dir, null).pass();
      if (passA != passB) {
        changed += 1;
        System.out.format("CHANGED %s: %s=%s %s=%s%n",
            dir.getPath().substring(root.length()).replaceFirst("^/", ""),
            labelA, passA ? "PASS" : "FAIL",
            labelB, passB ? "PASS" : "FAIL");
      }
    }
    if (changed == 0) {
      System.out.println("DIFF: none");
    } else {
      System.out.format("DIFF: %d test changed%n", changed);
    }
  }

  /** Write a job result: a header line followed by the captured diagnostics. */
  static void writeResult(PrintStream out, String id, Result result) {
    List<String> lines = new ArrayList<String>();
    for (String line : result.output.split("\n", -1)) {
      lines.add(line.replace("\r", ""));
    }
    // Drop the empty line after a trailing newline.
    while (!lines.isEmpty() && lines.get(lines.size() - 1).isEmpty()) {
      lines.remove(lines.size() - 1);
    }
    out.format("RESULT %s %d %d%n", id, result.exitCode, lines.size());
    for (String line : lines) {
      out.println(line);
    }
    out.flush();
  }

  /** Serve compile jobs read from standard input until end of input or a QUIT line. */
  static void worker(String[] args) throws IOException {
    PrintStream out = System.out;
    List<Compiler> compilers = new ArrayList<Compiler>();
    List<String> errors = new ArrayList<String>();
    for (int i = 1; i + 1 < args.length; i += 2) {
      Compiler compiler = null;
      String error = null;
      try {
        compiler = create(args[i], args[i + 1]);
      } catch (Throwable t) {
        error = t.toString().replace('\n', ' ');
      }
      compilers.add(compiler);
      errors.add(error);
      if (error != null) {
        out.format("LOADFAIL %d %s%n", compilers.size() - 1, error);
      }
    }
    out.format("READY %d%n", compilers.size());
    out.flush();

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    String line;
    while ((line = in.readLine()) != null) {
      if (line.equals("QUIT")) {
        break;
      }
      if (line.isEmpty()) {
        continue;
      }
      String[] header = line.split(" ");
      String id = header[0];
      int index = Integer.parseInt(header[1]);
      int argc = Integer.parseInt(header[2]);
      String[] argv = new String[argc];
      for (int i = 0; i < argc; ++i) {
        argv[i] = in.readLine();
      }
      Compiler compiler = index >= 0 && index < compilers.size() ? compilers.get(index) : null;
      if (compiler == null) {
        String error = index >= 0 && index < errors.size()
            ? errors.get(index) : "no such compiler version: " + index;
        writeResult(out, id, new Result(EXIT_INTERNAL_ERROR, error));
      } else {
        writeResult(out, id, compile(compiler, argv));
      }
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length > 0 && args[0].equals("--worker")) {
      worker(args);
    } else if (args.length == 2 || args.length == 3) {
      diff(args);
    } else {
      System.err.println("Usage: java TestRunner <[label=]spec1> <[label=]spec2> [testsRoot]");
      System.err.println("       java TestRunner --worker <type> <classpath> ...");
      System.exit(2);
    }
  }
}
