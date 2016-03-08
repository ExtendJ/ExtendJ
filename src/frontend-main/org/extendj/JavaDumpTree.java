/* Copyright (c) 2005-2008, Torbjorn Ekman
 *               2011-2014, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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

import java.util.Collection;

/**
 * Dump the parsed AST for some Java source files.
 */
public class JavaDumpTree extends Frontend {

  /**
   * Entry point.
   * @param args command-line arguments
   */
  public static void main(String args[]) {
    int exitCode = new JavaDumpTree().run(args);
    if (exitCode != 0) {
      System.exit(exitCode);
    }
  }

  /**
   * Initialize the compiler.
   */
  public JavaDumpTree() {
    super("Java AST Dumper", ExtendJVersion.getVersion());
  }

  /**
   * @param args command-line arguments
   * @return {@code true} on success, {@code false} on error
   * @deprecated Use run instead!
   */
  @Deprecated
  public static boolean compile(String args[]) {
    return 0 == new JavaDumpTree().run(args);
  }

  /**
   * Dump source file abstract syntax trees.
   * @param args command-line arguments
   * @return 0 on success, 1 on error, 2 on configuration error, 3 on system
   */
  public int run(String args[]) {
    return run(args, Program.defaultBytecodeReader(), Program.defaultJavaParser());
  }

  @Override
  protected int processCompilationUnit(CompilationUnit unit) {
    if (unit != null && unit.fromSource()) {
      try {
        Collection<Problem> errors = unit.parseErrors();
        if (!errors.isEmpty()) {
          processErrors(errors, unit);
          return EXIT_ERROR;
        }
      } catch (Throwable t) {
        System.err.println("Errors:");
        System.err.format("Fatal exception while processing %s:\n", unit.pathName());
        t.printStackTrace(System.err);
        return EXIT_UNHANDLED_ERROR;
      }
    }
    return EXIT_SUCCESS;
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected void processErrors(Collection<Problem> errors, CompilationUnit unit) {
    super.processErrors(errors, unit);
    if (program.options().hasOption("-notransform")) {
      System.out.println(unit.dumpTreeNoRewrite());
    } else {
      System.out.println(unit.dumpTree());
    }
  }

  @Override
  protected void processNoErrors(CompilationUnit unit) {
    if (program.options().hasOption("-notransform")) {
      System.out.println(unit.dumpTreeNoRewrite());
    } else {
      System.out.println(unit.dumpTree());
    }
  }

  @Override
  protected void initOptions() {
    super.initOptions();
    program.options().addKeyOption("-notransform");
  }
}
