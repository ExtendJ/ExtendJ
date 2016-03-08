/* Copyright (c) 2005-2008, Torbjorn Ekman
 *               2014-2016, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Frontend;
import org.extendj.ast.Problem;
import org.extendj.ast.Program;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

/**
 * Pretty-print some Java source files.
 */
public class JavaPrettyPrinter extends Frontend {

  /**
   * Entry point for the compiler frontend.
   * @param args command-line arguments
   */
  public static void main(String args[]) {
    int exitCode = new JavaPrettyPrinter().run(args);
    if (exitCode != 0) {
      System.exit(exitCode);
    }
  }

  /**
   * Initialize the compiler.
   */
  public JavaPrettyPrinter() {
    super("ExtendJ Java Pretty Printer", ExtendJVersion.getVersion());
  }

  /**
   * @param args command-line arguments
   * @return {@code true} on success, {@code false} on error
   * @deprecated Use run instead!
   */
  @Deprecated
  public static boolean compile(String args[]) {
    return 0 == new JavaPrettyPrinter().run(args);
  }

  /**
   * Pretty print the source files.
   * @param args command-line arguments
   * @return 0 on success, 1 on error, 2 on configuration error, 3 on system
   */
  public int run(String args[]) {
    return run(args, Program.defaultBytecodeReader(), Program.defaultJavaParser());
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected void processErrors(Collection<Problem> errors, CompilationUnit unit) {
    super.processErrors(errors, unit);
    try {
      unit.prettyPrint(new PrintStream(System.out, false, "UTF-8"));
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }

  @Override
  protected void processNoErrors(CompilationUnit unit) {
    try {
      unit.prettyPrint(new PrintStream(System.out, false, "UTF-8"));
    } catch (IOException e) {
      e.printStackTrace(System.err);
    }
  }
}
