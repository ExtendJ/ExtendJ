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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ExtendJ compiler runner.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class ExtendJCompiler extends Compiler {

  private final boolean newVM;
  private final boolean debug;
  private final String jarPath;

  /** Memoization map for used compiler classes. */
  private static final ConcurrentMap<String, Class<?>> classMap =
      new ConcurrentHashMap<String, Class<?>>();

  /**
   * @param jarPath Path to the ExtendJ jar
   * @param newVM set to {@code true} if a new JVM should be started
   * @param debug set to {@code true} if remote debuggin should be enabled
   */
  public ExtendJCompiler(String jarPath, boolean newVM, boolean debug) {
    super("extendj", jarPath);

    this.newVM = newVM;
    this.debug = debug;
    this.jarPath = jarPath;
  }

  @Override
  public int compile(String[] arguments, OutputStream out, OutputStream err) {
    InputStream in = new ByteArrayInputStream(new byte[0]);
    return invoke(arguments, in, out, err);
  }

  /**
   * Invoke ExtendJ using reflection (in order to access main class in
   * default package).
   *
   * @return Exit value of the compile process
   */
  public int invoke(String[] arguments, InputStream in,
      OutputStream outStream, OutputStream errStream) {

    final PrintStream out = new PrintStream(outStream);
    final PrintStream err = new PrintStream(errStream);

    if (newVM || debug) {
      List<String> cmd = new ArrayList<String>();
      cmd.add("java");
      cmd.add("-Xmx2g");
      if (debug) {
        cmd.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005");
      }
      cmd.add("-jar");
      cmd.add(jarPath);
      Collections.addAll(cmd, arguments);
      try {
        String[] cmdArray = cmd.toArray(new String[cmd.size()]);
        final Process proc = Runtime.getRuntime().exec(cmdArray);

        final Collection<String> stderrErrors = new LinkedList<String>();
        new Thread() {
          @Override
          public void run() {
            Scanner scanner = new Scanner(proc.getErrorStream());
            while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              if (stderrErrors.isEmpty() &&
                  line.equals("java.lang.NullPointerException"))
                stderrErrors.add(line);
              err.println(line);
            }
            scanner.close();
          }
        }.start();

        // Some versions of ExtendJ print error messages on stdout
        // and do not return a nozero exit code on error.
        final Collection<String> stdoutErrors = new LinkedList<String>();
        new Thread() {
          @Override
          public void run() {
            Scanner scanner = new Scanner(proc.getInputStream());
            while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              if (stdoutErrors.isEmpty() && line.equals("Errors:"))
                stdoutErrors.add(line);
              out.println(line);
            }
            scanner.close();
          }
        }.start();
        int exitValue = proc.waitFor();
        if (!stdoutErrors.isEmpty() || !stderrErrors.isEmpty()) {
          return 1;
        } else {
          return exitValue;
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return 1;
    }

    // Start new ExtendJ compilation in this JVM.
    // First, redirect I/O streams:
    InputStream stdin = System.in;
    PrintStream stdout = System.out;
    PrintStream stderr = System.err;
    System.setIn(in);
    System.setOut(out);
    System.setErr(err);

    try {
      if (!classMap.containsKey(jarPath)) {
        // Dynamically load ExtendJ class from Jar file.
        try {
          File jarFile = new File(jarPath);
          URL jarUrl = jarFile.toURI().toURL();
          URLClassLoader classLoader =
              new URLClassLoader(new URL[] { jarUrl }, getClass().getClassLoader());
          Class<?> exjMain = Class.forName("org.extendj.JavaCompiler", true, classLoader);
          // Memoize result:
          classMap.putIfAbsent(jarPath, exjMain);
        } catch (MalformedURLException e) {
          throw new Error(e);
        } catch (ClassNotFoundException e) {
          throw new Error(e);
        } catch (SecurityException e) {
          throw new Error(e);
        }
      }

      // Invoke compile method on the main compiler class:
      Class<?> exjMain = classMap.get(jarPath);
      Method compile = exjMain.getMethod("compile", new Class[] { String[].class } );
      boolean result = (Boolean) compile.invoke(null, new Object[] { arguments });
      return result ? 0 : 1;
    } catch (NoSuchMethodException e) {
      throw new Error(e);
    } catch (IllegalArgumentException e) {
      throw new Error(e);
    } catch (IllegalAccessException e) {
      throw new Error(e);
    } catch (InvocationTargetException e) {
      throw new Error(e);
    } finally {
      System.setIn(stdin);
      System.setOut(stdout);
      System.setErr(stderr);
    }
  }
}
