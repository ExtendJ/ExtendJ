package org.jastadd.jastaddj;
/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 *
 * Copyright (c) 2005-2008, Torbjorn Ekman
 *		 2011	    Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 */

import AST.*;

/**
 * Perform static semantic checks on a Java program.
 */
@SuppressWarnings("javadoc")
public class JavaChecker extends Frontend {
	public static void main(String args[]) {
		compile(args);
	}

	public static boolean compile(String args[]) {
		return new JavaChecker().process(
				args,
				new BytecodeParser(),
				new JavaParser() {
					@Override
					public CompilationUnit parse(java.io.InputStream is, String fileName) throws java.io.IOException, beaver.Parser.Exception {
						return new parser.JavaParser().parse(is, fileName);
					}
				}
				);
	}

	@Override
	protected String name() {
		return "Java Checker";
	}

	@Override
	protected String version() {
		return JastAddJVersion.getVersion();
	}
}
