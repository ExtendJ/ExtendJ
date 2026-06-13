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
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
@RunWith(Parameterized.class)
public class TestJava8 {

  private static final TestProperties properties = new TestProperties();
  static {
    properties.setProperty("compiler", "javac");
    properties.setProperty("extraOptions", "-Xlint:none");
    properties.exclude(tests.Tests.FAILING);
    properties.exclude(tests.Tests.EXCLUDE_JAVA7);
    properties.exclude(tests.Tests.EXCLUDE_JAVA8);
    properties.exclude(tests.Tests.JAVA9);
    properties.exclude(tests.Tests.JAVA10);
    properties.exclude(tests.Tests.JAVA11);
    properties.exclude(tests.Tests.EXTENDJ_ONLY);

    // Javac bugs cause these tests to crash the javac compiler:
    properties.exclude("jsr335/Semantics/FunctionalInterfaces/ReturnTypeSubstitutable/ShouldCompile/syntax22");
    properties.exclude("jsr335/Semantics/FunctionalInterfaces/TypeParameters/ShouldCompile/syntax05");
    properties.exclude("jsr335/Semantics/FunctionalInterfaces/TypeParameters/ShouldCompile/syntax06");
    properties.exclude("jsr335/Semantics/LambdaTypeAnalysis/AssignmentContext/ShouldCompile/syntax28");
  }

  private final String testDir;

  /**
   * Construct a new JastAdd test
   * @param testDir The base directory for the test
   */
  public TestJava8(String testDir) {
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
