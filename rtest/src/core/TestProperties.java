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

import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
@SuppressWarnings("serial")
public class TestProperties extends Properties {

  private final Collection<String> includeAdd = new LinkedList<String>();
  private final Collection<String> excludeAdd = new LinkedList<String>();

  @Override
  public String getProperty(String key, String defaultValue) {
    String value = System.getProperty(key, "");
    if (value.isEmpty()) {
      value = super.getProperty(key, defaultValue);
    }
    return value;
  }

  /**
   * Add a test to the list of included tests.
   * Can be overridden by the test property.
   * @param path the path to the test directory
   */
  public void include(String path) {
    includeAdd.add(path);
  }

  /**
   * Add some include paths.
   * @param paths
   */
  public void include(String... paths) {
    for (String path: paths) {
      include(path);
    }
  }

  /**
   * Add a test to the list of included tests.
   * Can be overridden by the test property.
   * @param path the path to the test directory
   */
  public void exclude(String path) {
    excludeAdd.add(path);
  }

  /**
   * Add some exclude paths.
   * @param paths
   */
  public void exclude(String... paths) {
    for (String path: paths) {
      exclude(path);
    }
  }

  /**
   * @return collection of included test directories
   */
  public Collection<String> includes() {
    Collection<String> includes = new LinkedList<String>();
    String testProperty = getProperty("test", "").trim();
    if (!testProperty.isEmpty()) {
      addPaths(includes, testProperty);
    } else {
      includes.addAll(includeAdd);
      addPaths(includes, getProperty("includes", ""));
    }
    return includes;
  }

  /**
   * @return collection of the excluded test directories
   */
  public Collection<String> excludes() {
    Collection<String> excludes = new LinkedList<String>();

    String testProperty = getProperty("test", "").trim();
    if (testProperty.isEmpty()) {
      excludes.addAll(excludeAdd);
      addPaths(excludes, getProperty("excludes", ""));
    }
    return excludes;
  }

  /**
   * Add comma-separated paths to list
   */
  private static void addPaths(Collection<String> list, String pathList) {
    String[] items = pathList.split(",");
    for (String item : items) {
      item = item.trim().replace('\\', '/');
      if (!item.isEmpty()) {
        list.add(item);
      }
    }
  }

}
