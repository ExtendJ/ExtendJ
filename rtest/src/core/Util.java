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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility methods for JastAdd testing
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class Util {

  /**
   * Root directory for all tests
   */
  public static final String TEST_ROOT = "tests";

  /**
   * Root directory for test output
   */
  public static final String TEMP_ROOT = "tmp";

  /**
   * Find all test directories
   * @param testRoot
   * @param testDirs
   * @param excludes
   */
  private static void addChildTestDirs(File testRoot, Collection<Object[]> testDirs,
    Collection<String> excludes) {

    for (File child: testRoot.listFiles()) {
      addTestDir(child, testDirs, excludes);
    }
  }

  private static void addTestDir(File dir,
      Collection<Object[]> testDirs, Collection<String> excludes) {

    if (dir.isDirectory()) {
      String path = dir.getPath().replace(File.separatorChar, '/');
      if (path.startsWith(TEST_ROOT + "/"))
        path = path.substring(TEST_ROOT.length()+1);
      for (String exclude: excludes) {
        if (path.startsWith(exclude)) {
          return;
        }
      }
      File resultFile = new File(dir, "Test.java");
      File propertiesFile = new File(dir, "Test.properties");
      if (resultFile.isFile() || propertiesFile.isFile()) {
        if (!skipTest(dir)) {
          testDirs.add(new Object[] { path });
        }
      } else {
        addChildTestDirs(dir, testDirs, excludes);
      }
    }
  }

  private static void addByPattern(File root, String pattern,
      Collection<Object[]> testDirs, Collection<String> excludes) {
    if (pattern.isEmpty()) {
      addTestDir(root, testDirs, excludes);
    } else {
      int index = pattern.indexOf('/');
      String part, rest;
      if (index == -1) {
        part = pattern;
        rest = "";
      } else {
        part = pattern.substring(0, index);
        rest = pattern.substring(index+1, pattern.length());
      }
      if (part.indexOf('*') == -1) {
        addByPattern(new File(root, part), rest, testDirs, excludes);
      } else if (part.equals("**")) {
        addByPattern(root, rest, testDirs, excludes);
        addByPattern(root, "*/**/" + rest, testDirs, excludes);
      } else if (root.isDirectory()) {
        for (File file: root.listFiles()) {
          if (patternMatch(file.getName().toCharArray(), 0, part.toCharArray(), 0)) {
            addByPattern(file, rest, testDirs, excludes);
          }
        }
      }
    }
  }

  private static boolean patternMatch(char[] name, int ni, char[] pattern, int pi) {

    if (ni >= name.length && pi >= pattern.length) {
      return true;
    }

    char p = pattern[pi];

    if (p == '*') {
      if (ni >= name.length) {
        return patternMatch(name, ni, pattern, pi+1);
      } else {
        return patternMatch(name, ni+1, pattern, pi) || patternMatch(name, ni+1, pattern, pi+1);
      }
    } else {
      if (ni >= name.length) {
        return false;
      } else {
        char n = name[ni];
        return n == p && patternMatch(name, ni+1, pattern, pi+1);
      }
    }
  }

  /**
   * @param testDir
   * @return <code>true</code> if the test should be skipped
   */
  private static boolean skipTest(File testDir) {
    return false;
  }

  /**
   * @param properties
   * @return A collection of String arrays containing the test directories
   */
  public static Iterable<Object[]> getTests(TestProperties properties) {
    List<Object[]> testDirs = new LinkedList<Object[]>();

    Collection<String> includes = properties.includes();
    Collection<String> excludes = properties.excludes();

    if (includes.isEmpty()) {
      addTestDir(new File(TEST_ROOT), testDirs, excludes);
    } else {
      for (String include: includes) {
        addByPattern(new File(TEST_ROOT), include.replace('\\', '/'),
            testDirs, excludes);
      }
    }

    // sort the tests lexicographically
    Collections.sort(testDirs, new Comparator<Object[]>() {
      @Override
      public int compare(Object[] a, Object[] b) {
        return ((String)a[0]).compareTo((String)b[0]);
      }
    });
    return testDirs;
  }

  /**
   * @param propertiesFile
   * @return The properties loaded from the given file
   */
  private static TestProperties getProperties(File propertiesFile) {
    TestProperties properties = new TestProperties();
    try {
      FileInputStream in = new FileInputStream(propertiesFile);
      properties.load(in);
      in.close();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }

  /**
   * @param sourceFile
   * @return Test configuration loaded from comments
   */
  private static TestProperties readPropertyComments(File sourceFile) {
    TestProperties properties = new TestProperties();
    try {
      Scanner scanner = new Scanner(new FileInputStream(sourceFile));
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.startsWith("//")) {
          int index = line.indexOf('.');
          if (index == 2 || index == 3) {
            // this is a property definition
            String property = line.substring(index+1).trim();
            if (!property.isEmpty()) {
              properties.load(new StringReader(property));
            }
          }
        } else {
          break;
        }
      }
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }

  /**
   * Try to read the Test.properties file, if that fails read properties from
   * Test.java comments.
   * @param testDir
   * @return test properties
   */
  public static TestProperties getTestProperties(File testDir) {
    File propertiesFile = new File(testDir, "Test.properties");
    if (propertiesFile.isFile()) {
      // Read test config from the .properties file.
      return Util.getProperties(propertiesFile);
    } else {
      File sourceFile = new File(testDir, "Test.java");
      if (sourceFile.isFile()) {
        // Read test config from the .java file.
        return Util.readPropertyComments(sourceFile);
      } else {
        return new TestProperties();
      }
    }
  }
}
