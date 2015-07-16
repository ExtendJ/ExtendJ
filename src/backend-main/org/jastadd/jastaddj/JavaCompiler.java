/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 *
 * Copyright (c) 2015, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 */
package org.jastadd.jastaddj;

/**
 * Legacy entry-point for ExtendJ provided for backward-compatibility.
 * This class just delegates to org.jastadd.extendj.JavaCompiler.
 */
public class JavaCompiler {
  public static void main(String[] args) {
    org.jastadd.extendj.JavaCompiler.main(args);
  }
}
