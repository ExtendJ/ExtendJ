import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Compare two ExtendJ versions by compiling every test under the given test root,
 * reporting tests where the two compiler versions disagree on whether compilation succeeds.
 *
 * <p>This is a testing tool useful for quickly checking for regressions during
 * development.  By running all ExtendJ compilations in a single JVM instance
 * the full regression test run becomes much faster.  However, there are
 * compatibility problems that can arise if the JVM version running this tool
 * does not match the target Java version under test.
 *
 * <p>Each ExtendJ version is specified with a classpath (an ExtendJ jar file and/or compiled
 * class directories), optionally prefixed by a label.
 * A test is considered to compile successfully if the compile method returns true and produces no output
 * to stderr/stdout.
 *
 * <p>Usage:
 * <pre>
 *   javac DiffRunner.java
 *   java DiffRunner &lt;[label=]classpath1&gt; &lt;[label=]classpath2&gt; [testsRoot]
 * </pre>
 * The optional label prefix names the compiler versions in the report. It defaults to
 * "baseline" for the first version and "new" for the second. The tests root
 * defaults to "tests". For example, comparing the current build against a
 * baseline jar:
 * <pre>
 *   java DiffRunner baseline.jar ../java8/extendj.jar
 * </pre>
 */
public class DiffRunner {

  /** Load org.extendj.JavaCompiler.compile(String[]) from a classpath of jars/class directories. */
  static Method load(String classpath) throws Exception {
    String[] paths = classpath.split(File.pathSeparator);
    URL[] urls = new URL[paths.length];
    for (int i = 0; i < paths.length; ++i) {
      File file = new File(paths[i]);
      if (!file.exists()) {
        throw new IllegalArgumentException("no such file or directory: " + paths[i]);
      }
      urls[i] = file.toURI().toURL();
    }
    URLClassLoader classLoader = new URLClassLoader(urls, DiffRunner.class.getClassLoader().getParent());
    Class<?> compiler = Class.forName("org.extendj.JavaCompiler", true, classLoader);
    return compiler.getMethod("compile", String[].class);
  }

  static void printVersion(Method compile) throws Exception {
    String[] argv = { "-version" };
    compile.invoke(null, (Object) argv);
  }

  static boolean pass(Method compile, String[] argv) {
    PrintStream stdout = System.out;
    PrintStream stderr = System.err;
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    PrintStream capture = new PrintStream(buf);
    System.setOut(capture);
    System.setErr(capture);
    boolean ok = false;
    try {
      ok = (Boolean) compile.invoke(null, (Object) argv);
    } catch (Throwable t) {
      // Suppressed.
    } finally {
      System.setOut(stdout);
      System.setErr(stderr);
    }
    return ok && buf.size() == 0;
  }

  /** Split the label/classpath argument. */
  static String[] splitLabel(String arg, String defaultLabel) {
    int eq = arg.indexOf('=');
    if (eq < 0) {
      return new String[] { defaultLabel, arg };
    }
    return new String[] { arg.substring(0, eq), arg.substring(eq + 1) };
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2 || args.length > 3) {
      System.err.println("Usage: java DiffRunner <[label=]classpath1> <[label=]classpath2> [testsRoot]");
      System.exit(2);
    }
    String[] buildA = splitLabel(args[0], "baseline");
    String[] buildB = splitLabel(args[1], "new");
    String labelA = buildA[0];
    String labelB = buildB[0];
    String root = args.length > 2 ? args[2] : "tests";
    Method compileA = load(buildA[1]);
    Method compileB = load(buildB[1]);

    final List<File> dirs = new ArrayList<File>();
    Files.walk(Paths.get(root))
        .filter(p -> p.toString().endsWith(".java"))
        .map(p -> p.getParent().toFile())
        .distinct()
        .forEach(dirs::add);
    Collections.sort(dirs);
    System.out.format("Found %d tests under %s%n", dirs.size(), Paths.get(root).toAbsolutePath());

    System.out.format("%s: ", labelA);
    printVersion(compileA);
    System.out.format("%s: ", labelB);
    printVersion(compileB);

    int changed = 0;
    for (File dir : dirs) {
      File[] sources = dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File d, String name) {
          return name.endsWith(".java");
        }
      });
      if (sources == null || sources.length == 0) {
        continue;
      }
      List<String> argv = new ArrayList<String>();
      argv.add("-classpath");
      argv.add(dir.getAbsolutePath());
      for (File source : sources) {
        argv.add(source.getAbsolutePath());
      }
      String[] compileArgs = argv.toArray(new String[0]);
      boolean passA = pass(compileA, compileArgs);
      boolean passB = pass(compileB, compileArgs);
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
}
