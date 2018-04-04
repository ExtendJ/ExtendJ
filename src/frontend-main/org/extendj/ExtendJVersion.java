/* Copyright (c) 2013, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.extendj;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Helper class to get the current compiler version.
 *
 * <p>The version string is read from the property files
 * org/extendj/Version.properties and org/extendj/BuildInfo.properties.  These
 * files should be generated during the build process. If either is missing
 * then there is some problem in the build script.
 *
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class ExtendJVersion {

  private static final String versionString;

  static {
    String version;
    ResourceBundle resources = null;
    try {
      resources = ResourceBundle.getBundle("Version");
      version = resources.getString("version");
    } catch (MissingResourceException e) {
      version = "version ?";
    }
    try {
      resources = ResourceBundle.getBundle("BuildInfo");
      version +=  " " + resources.getString("moduleName");
    } catch (MissingResourceException e) {
    }
    versionString = version;
  }

  /**
   * @return compiler version string, including Java support level.
   */
  public static String getVersion() {
    return versionString;
  }
}
