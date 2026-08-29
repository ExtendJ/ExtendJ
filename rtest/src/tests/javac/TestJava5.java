// SPDX-License-Identifier: BSD-3-Clause
package tests.javac;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import core.TestProperties;
import core.TestRunner;
import core.Util;

/**
 * A parameterized Junit test to test Javac.
 */
@RunWith(Parameterized.class)
public class TestJava5 {

  private static final TestProperties properties = new TestProperties();
  static {
    properties.setProperty("compiler", "javac");
    properties.setProperty("extraOptions", "-Xlint:none,-source,1.5,-target,1.5");
    properties.exclude(tests.Tests.JAVA6);
    properties.exclude(tests.Tests.JAVA7);
    properties.exclude(tests.Tests.JAVA8);
    properties.exclude(tests.Tests.JAVA9);
    properties.exclude(tests.Tests.JAVA10);
    properties.exclude(tests.Tests.JAVA11);
    properties.exclude(tests.Tests.FAILING);
    properties.exclude(tests.Tests.EXTENDJ_ONLY);
  }

  private final String testDir;

  /**
   * @param testDir The base directory for the test
   */
  public TestJava5(String testDir) {
    this.testDir = testDir;
  }

  /**
   * Run the JastAdd test
   */
  @Test
  public void runTest() throws IOException {
    TestRunner.runTest(testDir, properties);
  }

  @SuppressWarnings("javadoc")
  @Parameters(name = "{0}")
  public static Iterable<Object[]> getTests() {
    return Util.getTests(properties);
  }
}
