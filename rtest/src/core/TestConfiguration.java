/* Copyright (c) 2013-2016, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * Configuration for a single test - includes info about the test to run, the
 * test root directory, and compiler configuration for the test run.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class TestConfiguration {
  protected File testDir;
  protected final TestProperties testProperties;
  protected final Result expected;
  protected final File tmpDir;
  protected final Compiler compiler;
  protected final boolean verbose;

  public TestConfiguration(String testName, Properties testSuiteProperties) {
    testDir = new File(Util.TEST_ROOT, testName);
    testProperties = Util.getTestProperties(testDir);
    expected = getExpectedResult(testProperties);
    tmpDir = setupTemporaryDirectory(testName);

    verbose = testProperties.getProperty("verbose", "").equals("true");

    // Set up compiler option.
    testProperties.setProperty("compiler", testSuiteProperties.getProperty("compiler", "extendj"));
    testProperties.setProperty("extendj.jar",
        testSuiteProperties.getProperty("extendj.jar", "extendj.jar"));
    compiler = getCompiler(testProperties);
  }

  private static Compiler getCompiler(Properties props) {
    if (props.getProperty("compiler", "").equals("extendj")) {
      // Compile with ExtendJ.
      return new ExtendJCompiler(props.getProperty("extendj.jar"),
          props.getProperty("fork", "").equals("true"),
          props.getProperty("debug", "").equals("true"));
    } else {
      // Compile with javac.
      return new JavacCompiler(false);
    }
  }

  /**
   * Set up the temporary directory - create it if it does not exist
   * and clean it if it does already exist.
   * @param testName The temporary directory
   */
  private static File setupTemporaryDirectory(String testName) {
    File tmpDir = new File(Util.TEMP_ROOT, testName);

    if (!tmpDir.exists()) {
      // create directory with intermediate parent directories
      tmpDir.mkdirs();
    } else {
      // clean temporary directory
      cleanDirectory(tmpDir);
    }
    return tmpDir;
  }

  private static Result getExpectedResult(Properties props) {

    if (!props.containsKey("result")) {
      return Result.EXEC_PASSED;
    }

    String result = props.getProperty("result");
    if (result.equals("COMPILE_PASSED") || result.equals("COMPILE_PASS")) {
      return Result.COMPILE_PASSED;
    } else if (result.equals("COMPILE_FAILED") || result.equals("COMPILE_FAIL")) {
      return Result.COMPILE_FAILED;
    } else if (result.equals("COMPILE_WARNING") || result.equals("COMPILE_WARN")) {
      return Result.COMPILE_WARNING;
    } else if (result.equals("COMPILE_ERR_OUTPUT")) {
      return Result.COMPILE_ERR_OUTPUT;
    } else if (result.equals("EXEC_PASSED") || result.equals("EXEC_PASS")) {
      return Result.EXEC_PASSED;
    } else if (result.equals("EXEC_FAILED") || result.equals("EXEC_FAIL")) {
      return Result.EXEC_FAILED;
    } else if (result.equals("COMPILE_OUT") || result.equals("COMPILE_OUTPUT")) {
      return Result.COMPILE_OUTPUT;
    } else {
      fail("Unknown expected result: " + result);
      return Result.EXEC_PASSED;
    }
  }

  /**
   * Recursively remove all files and folders in a directory
   * @param dir The directory to nuke
   */
  private static void cleanDirectory(File dir) {
    for (File file: dir.listFiles()) {
      if (!file.isDirectory()) {
        file.delete();
      } else {
        cleanDirectory(file);
        file.delete();
      }
    }
  }

  public String extendjJar() {
    return testProperties.getProperty("extendj.jar");
  }
}
