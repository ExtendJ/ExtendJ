/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 *
 * Copyright (c) 2005-2008, Torbjorn Ekman
 *               2011, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 */

package org.jastadd.jastaddj;

import AST.*;

@SuppressWarnings("javadoc")
public class JavaCompiler extends Frontend {
	public static void main(String args[]) {
		if(!compile(args))
			System.exit(1);
	}

	public static boolean compile(String args[]) {
		boolean result = new JavaCompiler().process(
				args,
				new BytecodeParser(),
				new JavaParser() {
					@Override
					public CompilationUnit parse(java.io.InputStream is,
							String fileName) throws java.io.IOException,
							beaver.Parser.Exception {

						return new parser.JavaParser().parse(is, fileName);
					}
				});
		return result;
	}
	@Override
	protected void processNoErrors(CompilationUnit unit) {
		unit.transformation();
		unit.generateClassfile();
	}

	@Override
	protected String name() {
		return "JastAddJ";
	}
	@Override
	protected String version() {
		return JastAddJVersion.getVersion();
	}
}
