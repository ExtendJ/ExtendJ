/* Copyright (c) 2013, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Lund University nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.jastadd.jastaddj;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * JastAddJ version string provider.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class JastAddJVersion {

	private static final String versionString;

	static {
		String version;
		ResourceBundle resources = null;
		try {
			resources = ResourceBundle.getBundle("Version");
		} catch (MissingResourceException e) {
			throw new Error("Could not open Version resource bundle");
		}
		version = resources.getString("version");
		try {
			resources = ResourceBundle.getBundle("JavaSupportLevel");
		} catch (MissingResourceException e) {
			throw new Error("Could not open JavaSupportLevel resource bundle");
		}
		versionString = version +  " " + resources.getString("javaVersion");
	}

	/**
 	 * @return Version string, including Java support level.
 	 */
	public static String getVersion() {
		return versionString;
	}
}
