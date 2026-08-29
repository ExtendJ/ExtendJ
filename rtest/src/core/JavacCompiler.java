// SPDX-License-Identifier: BSD-3-Clause
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
 */
public class JavacCompiler extends Compiler {

  private final boolean newVM;
  private final String execPath;

  /**
   * @param execPath external javac executable, or empty for the system Java compiler
   * @param newVM run in separte VM
   */
  public JavacCompiler(String execPath, boolean newVM) {
    super("javac", execPath.isEmpty() ? getVersion() : execPath);

    this.execPath = execPath;
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

    if (newVM || !execPath.isEmpty()) {
      List<String> cmd = new ArrayList<String>();
      if (!execPath.isEmpty()) {
        cmd.add(execPath);
      } else {
        cmd.add("java");
        // TODO(jesper): build the jar file
        cmd.add("-jar");
        cmd.add("tools/javacjar.jar");
      }
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
