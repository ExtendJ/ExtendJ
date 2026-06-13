/* Copyright (c) 2013-2018, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *
 *   3. Neither the name of the copyright holder nor the names of its contributors
 *      may be used to endorse or promote products derived from this software
 *      without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Collections;
import java.util.Arrays;

/**
 * Utility methods for running JastAdd unit tests.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class TestRunner {

  private static String SYS_LINE_SEP = System.getProperty("line.separator");
  private static String RUNTIME_CLASSES = "runtime/classes";

  /**
   * Run the unit test in testDir with the given JastAdd configuration.
   */
  public static void runTest(String testName, Properties testSuiteProperties)
      throws IOException {
    TestConfiguration config = new TestConfiguration(testName, testSuiteProperties);

    Result expected = config.expected;

    // Compile generated code with the selected compiler.
    compileSources(config, testSuiteProperties.getProperty("extraOptions", "").trim());

    switch (expected) {
      case COMPILE_OUTPUT:
        compareOutput("compile.out", config.tmpDir, config.testDir);
        return;
      case COMPILE_PASSED:
      case COMPILE_WARNING:
        return;
      case COMPILE_FAILED:
        if (config.compiler.name.equals("extendj")) {
          checkExtendJErrorOutput(config.tmpDir, config.testDir);
        }
        return;
      case COMPILE_ERR_OUTPUT:
        compareCompileErrOutput(config.tmpDir, config.testDir);
        return;
      case EXEC_PASSED:
      case EXEC_FAILED:
        // Execute the compiled code.
        executeCode(config, expected);

        // Compare the output with the expected output.
        compareErrorOutput(config.tmpDir, config.testDir);
        compareOutput("out", config.tmpDir, config.testDir);
        return;
    }
  }

  /**
   * Check if there is an extendj.err.expected file in the test directory,
   * if so we assert that the compiler error output equals the content of
   * that file.
   */
  private static void checkExtendJErrorOutput(File tmpDir, File testDir)
      throws IOException {
    File expected = new File(testDir, "extendj.err.expected");
    if (expected.isFile()) {
      File actual = new File(tmpDir, "compile.err");
      // Use containsExactly to ignore error message order:
      TestUtil.testThat(readErrorMessages(actual))
          .containsExactly("Compile errors differ.", readErrorMessages(expected));
    }
  }

  /**
   * Compare the error output from the compiler against the expected error output.
   */
  private static void compareCompileErrOutput(File tmpDir, File testDir)
      throws IOException {
    File expected = expectedCompileErrorOutput(testDir);
    File actual = new File(tmpDir, "compile.err");
    // Use containsExactly to ignore error message order:
    TestUtil.testThat(readErrorMessages(actual))
        .containsExactly("Compile errors differ.", readErrorMessages(expected));
  }

  private static File expectedCompileErrorOutput(File testDir) {
    boolean windows = System.getProperty("os.name").startsWith("Windows");
    if (windows) {
      // First try opening .win file.
      File file = new File(testDir, "compile.err.expected.win");
      if (file.isFile()) {
        return file;
      }
    }
    // Open default file.
    return new File(testDir, "compile.err.expected");
  }

  /**
   * Compare the generated error output to the expected error output
   */
  private static void compareErrorOutput(File tmpDir, File testDir) {
    try {
      File expected = new File(testDir, "err.expected");
      File actual = new File(tmpDir, "err");
      assertEquals("Error output files differ", readFileToString(expected),
          readFileToString(actual));
    } catch (IOException e) {
      fail("IOException occurred while comparing error output: " + e.getMessage());
    }
  }

  /**
   * Compare the generated output to the expected output
   */
  private static void compareOutput(String file, File tmpDir, File testDir) {
    try {
      File expected = new File(testDir, file + ".expected");
      File actual = new File(tmpDir, file);
      assertEquals("Output files differ",
          readFileToString(expected),
          readFileToString(actual));
    } catch (IOException e) {
      fail("IOException occurred while comparing output: " + e.getMessage());
    }
  }

  /**
   * Reads an entire file to a string object.
   *
   * <p>If the file does not exist an empty string is returned.
   *
   * <p>The system dependent line separator char sequence is replaced by
   * the newline character.
   *
   * @throws FileNotFoundException
   */
  private static String readFileToString(File file) throws IOException {
    if (!file.isFile()) {
      return "";
    }

    FileInputStream in = new FileInputStream(file);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    while (true) {
      int size = in.read(buffer);
      if (size == -1) {
        break;
      }
      out.write(buffer, 0, size);
    }
    in.close();
    out.close();
    return out.toString("UTF-8").replace(SYS_LINE_SEP, "\n").trim();
  }

  /**
   * Reads compile errors to a list of strings.
   *
   * <p>Backslashes are replaced by forward slashes.
   *
   * <p>The system dependent line separator char sequence is replaced by
   * the newline character.
   */
  private static List<String> readErrorMessages(File file) throws IOException {
    String data = readFileToString(file).replace('\\', '/');
    if (data.isEmpty()) {
      return Collections.emptyList();
    }
    // TODO: remove empty lines.
    return Arrays.asList(data.split("\n"));
  }

  /**
   * Run the compiled test program.
   */
  private static void executeCode(TestConfiguration config, Result expected) {
    Properties props = config.testProperties;
    File tmpDir = config.tmpDir;
    File testDir = config.testDir;
    StringBuffer errors = new StringBuffer();

    String classpath = tmpDir.getPath();
    if (props.containsKey("classpath")) {
      String addClasspath = config.testProperties.getProperty("classpath", "").trim();
      if (!addClasspath.isEmpty()) {
        addClasspath = addClasspath.replace(':', File.pathSeparatorChar);
        addClasspath = addClasspath.replace("@RUNTIME_CLASSES@", RUNTIME_CLASSES);
        addClasspath = addClasspath.replace("@TEST_DIR@", testDir.getPath());
        addClasspath = addClasspath.replace("@TMP_DIR@", tmpDir.getPath());
        addClasspath = addClasspath.replace("@TEMP_DIR@", tmpDir.getPath());
        addClasspath = addClasspath.replace("@EXTENDJ_LIB@", config.extendjJar());
        classpath += File.pathSeparator + addClasspath;
      }
    }

    List<String> cmd = new ArrayList<String>();
    cmd.add("java");
    if (props.getProperty("execDebug", "").equals("true")) {
      // Start the compiled code in debug mode (suspended).
      cmd.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005");
    }
    cmd.add("-classpath");
    cmd.add(classpath);

    if (props.containsKey("javaOptions")) {
      cmd.add(props.getProperty("javaOptions"));
    }

    cmd.add("Test");

    try {
      String[] cmdArray = cmd.toArray(new String[cmd.size()]);
      Process proc = Runtime.getRuntime().exec(cmdArray);
      // Write output to file.
      InputStream in = proc.getInputStream();
      OutputStream out = new FileOutputStream(new File(tmpDir, "out"));
      InputStream errIn = proc.getErrorStream();
      OutputStream errOut = new FileOutputStream(new File(tmpDir, "err"));
      int data;
      while ((data = in.read()) != -1) {
        out.write(data);
      }
      out.close();
      while ((data = errIn.read()) != -1) {
        errOut.write(data);
        errors.append((char) data);
      }
      errOut.close();
      int exitValue = proc.waitFor();
      if (exitValue == 0) {
        if (expected == Result.EXEC_FAILED) {
          fail("Code execution passed when expected to fail");
        }
      }
      return;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (expected != Result.EXEC_FAILED) {
      fail("Code execution failed when expected to pass:\n" +
          errors.toString());
    }
  }

  /**
   * Compile generated source files.
   */
  private static void compileSources(TestConfiguration config, String extraOptions) {
    Properties props = config.testProperties;

    String compileOrder = props.getProperty("compile_order", "");
    String sourceOrder = props.getProperty("source_order", "");
    if (!compileOrder.isEmpty()) {
      // Compile files in custom order
      for (String sourceObj : compileOrder.split(",")) {
        File sourceFile = new File(config.testDir, sourceObj.trim());
        Collection<String> sourceFiles = new LinkedList<String>();
        sourceFiles.add(sourceFile.getPath());
        compileSources(config, sourceFiles, extraOptions);
      }
    } else if (!sourceOrder.isEmpty()) {
      // use custom source order
      Collection<String> sourceFiles = new LinkedList<String>();
      for (String sourceObj : sourceOrder.split(",")) {
        File sourceFile = new File(config.testDir, sourceObj.trim());
        sourceFiles.add(sourceFile.getPath());
      }
      compileSources(config, sourceFiles, extraOptions);
    } else {
      String sources = props.getProperty("sources", "");
      Collection<String> sourceFiles;
      if (sources.isEmpty()) {
        sourceFiles = collectSourceFiles(config.tmpDir);
        sourceFiles.addAll(collectSourceFiles(config.testDir));
      } else {
        sourceFiles = new LinkedList<String>();
        // use source list from test properties
        for (String sourceFile: sources.split(",")) {
          sourceFile = sourceFile.replace('/', File.separatorChar);
          sourceFiles.add(config.testDir.getPath() + File.separator + sourceFile);
        }
      }
      compileSources(config, sourceFiles, extraOptions);
    }
  }

  private static void compileSources(TestConfiguration config, Collection<String> sourceFiles,
      String extraOptions) {
    List<String> args = new ArrayList<String>();

    args.add("-d");
    args.add(config.tmpDir.getPath());

    String classpath = config.testProperties.getProperty("classpath", "").trim();
    if (!classpath.isEmpty()) {
      classpath = classpath.replace(':', File.pathSeparatorChar);
      args.add("-classpath");
      classpath = classpath.replace("@RUNTIME_CLASSES@", RUNTIME_CLASSES);
      classpath = classpath.replace("@TEST_DIR@", config.testDir.getPath());
      classpath = classpath.replace("@TMP_DIR@", config.tmpDir.getPath());
      classpath = classpath.replace("@TEMP_DIR@", config.tmpDir.getPath());
      classpath = classpath.replace("@EXTENDJ_LIB@", config.extendjJar());
      args.add(classpath);
    }

    String sourcepath = config.testProperties.getProperty("sourcepath", "").trim();
    if (!sourcepath.isEmpty()) {
      sourcepath.replace(':', File.pathSeparatorChar);
      args.add("-sourcepath");
      sourcepath = sourcepath.replace("@TEST_DIR@", config.testDir.getPath());
      sourcepath = sourcepath.replace("@TMP_DIR@", config.tmpDir.getPath());
      sourcepath = sourcepath.replace("@TEMP_DIR@", config.tmpDir.getPath());
      args.add(sourcepath);
    }

    // Add compiler options from the test suite.
    if (!extraOptions.isEmpty()) {
      for (String option : extraOptions.split(",")) {
        args.add(option);
      }
    }

    // Add compiler options from the test configuration.
    String options = config.testProperties.getProperty("options", "").trim();
    if (!options.isEmpty()) {
      for (String option : options.split(",")) {
        args.add("-" + option);
      }
    }

    args.addAll(sourceFiles);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayOutputStream err = new ByteArrayOutputStream();

    try {
      String[] arguments = args.toArray(new String[args.size()]);

      int exitValue = config.compiler.compile(arguments, out, err);

      if (err.size() > 0) {
        try {
          String errors = err.toString("UTF-8");
          PrintStream file = new PrintStream(
              new FileOutputStream(new File(config.tmpDir, "compile.err")), false, "UTF-8");
          file.print(errors);
          file.close();
        } catch (IOException e) {
          fail("Failed to write compile error output file!");
        }
      }

      if (config.expected == Result.COMPILE_OUTPUT && out.size() > 0) {
        try {
          String output = out.toString("UTF-8");
          PrintStream file = new PrintStream(
              new FileOutputStream(new File(config.tmpDir, "compile.out")), false, "UTF-8");
          file.print(output);
          file.close();
        } catch (IOException e) {
          fail("Failed to write compile error output file!");
        }
      }

      if (exitValue == 0) {
        Result result = err.size()==0 ? Result.COMPILE_PASSED : Result.COMPILE_WARNING;
        if (result != config.expected) {
          if (result == Result.COMPILE_WARNING) {
            fail("Compilation produced unexpected warning:\n" + err.toString());
          } else if (config.expected == Result.COMPILE_FAILED) {
            fail("Compilation passed when expected to fail!");
          }
        }
      } else {
        if (config.expected != Result.COMPILE_FAILED
            && config.expected != Result.COMPILE_ERR_OUTPUT) {
          fail("Compilation failed when expected to pass:\n" + err.toString());
        }
      }

      if (err.size() > 0 && config.verbose) {
        System.err.println(err.toString());
      }
    } finally {
      // Close streams.
      try {
        out.close();
      } catch (IOException e) {
      }
      try {
        err.close();
      } catch (IOException e) {
      }
    }
  }

  /**
   * Collect all source file names in the test directory
   */
  private static Collection<String> collectSourceFiles(File dir) {
    Collection<String> sourceFiles = new LinkedList<String>();
    for (File file: dir.listFiles()) {
      if (!file.isDirectory() && file.getName().endsWith(".java")) {
        sourceFiles.add(file.getPath());
      } else if (file.isDirectory()) {
        sourceFiles.addAll(collectSourceFiles(file));
      }
    }
    return sourceFiles;
  }

}
