/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 *
 * Copyright (c) 2005-2008, Torbjorn Ekman
 *               2011-2014, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 */
package org.jastadd.jastaddj;

import AST.*;

import java.util.*;

/**
 * Dump the parsed AST for some Java source files.
 */
class JavaDumpTree extends Frontend {

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

	
	private final JavaParser parser;
	private final BytecodeParser bytecodeParser;

	/**
	 * Initialize the compiler.
	 */
	public JavaDumpTree() {
		super("Java AST Dumper", JastAddJVersion.getVersion());
		parser = new JavaParser() {
			@Override
			public CompilationUnit parse(java.io.InputStream is,
					String fileName) throws java.io.IOException,
					beaver.Parser.Exception {
				return new parser.JavaParser().parse(is, fileName);
			}
		};
		bytecodeParser = new BytecodeParser();
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
		return run(args, bytecodeParser, parser);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void processErrors(Collection errors, CompilationUnit unit) {
		super.processErrors(errors, unit);
		System.out.println(unit.dumpTreeNoRewrite());
	}

	@Override
	protected void processNoErrors(CompilationUnit unit) {
		System.out.println(unit.dumpTreeNoRewrite());
	}
}
