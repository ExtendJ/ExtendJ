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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * javac compiler wrapper
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class JavacCompiler extends Compiler {

  private final boolean newVM;

  /**
   * Constructor
   * @param newVM
   */
  public JavacCompiler(boolean newVM) {
    super("javac", getVersion());

    this.newVM = newVM;
  }

  @Override
  public int compile(String[] arguments, OutputStream out, OutputStream err) {
    InputStream in = new ByteArrayInputStream(new byte[0]);
    return invoke(addExtraOptions(arguments), in, out, err);
  }

  protected String[] addExtraOptions(String[] arguments) {
    String[] result = new String[arguments.length + 1];
    System.arraycopy(arguments, 0, result, 1, arguments.length);
    result[0] = "-g";
    return result;
  }

  /**
   * Runs the configured javac compiler.
   * @return Exit value of the compile process
   */
  public int invoke(String[] arguments, InputStream in,
      final OutputStream out, final OutputStream err) {

    if (newVM) {
      List<String> cmd = new ArrayList<String>();
      cmd.add("java");
      // TODO(jesper): build the jar file
      cmd.add("-jar");
      cmd.add("tools/javacjar.jar");
      for (String arg : arguments) {
        cmd.add(arg);
      }
      try {
        String[] cmdArray = cmd.toArray(new String[cmd.size()]);
        final Process proc = Runtime.getRuntime().exec(cmdArray);
        Thread errThread = new Thread() {
          @Override
          public void run() {
            PrintStream ps = new PrintStream(err);
            Scanner scanner = new Scanner(proc.getErrorStream());
            while (scanner.hasNextLine()) {
              ps.println(scanner.nextLine());
            }
            scanner.close();
          }
        };
        Thread outThread = new Thread() {
          @Override
          public void run() {
            PrintStream ps = new PrintStream(out);
            Scanner scanner = new Scanner(proc.getInputStream());
            while (scanner.hasNextLine()) {
              ps.println(scanner.nextLine());
            }
            scanner.close();
          }
        };
        errThread.start();
        outThread.start();
        int exitValue = proc.waitFor();
        outThread.join();
        errThread.join();
        return exitValue;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return 1;
    }

    PrintStream stdout = System.out;
    try {
      System.setOut(new PrintStream(out));
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      // TODO(jesper): setting out as the output stream seems to not work...
      return compiler.run(in, null, err, arguments);
    } finally {
      System.setOut(stdout);
    }

  }

  /**
   * @return The version of this compiler
   */
  public static String getVersion() {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    InputStream in = new ByteArrayInputStream(new byte[0]);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    compiler.run(in, out, err, new String[] { "-version" });
    return err.toString().trim();
  }
}
