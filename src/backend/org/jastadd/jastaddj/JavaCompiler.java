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

import java.io.File;

import AST.*;

/**
 * Compile Java programs.
 */
public class JavaCompiler extends Frontend {

	/**
	 * Entry point for the compiler frontend.
	 * @param args command-line arguments
	 */
	public static void main(String args[]) {
		int exitCode = new JavaCompiler().run(args);
		if (exitCode != 0) {
			System.exit(exitCode);
		}
	}

	private final JavaParser parser;
	private final BytecodeParser bytecodeParser;

	/**
	 * Initialize the compiler.
	 */
	public JavaCompiler() {
		super("JastAddJ", JastAddJVersion.getVersion());
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
		return 0 == new JavaCompiler().run(args);
	}

	/**
	 * Run the compiler.
	 * @param args command-line arguments
	 * @return 0 on success, 1 on error, 2 on configuration error, 3 on system
	 */
	public int run(String args[]) {
		return run(args, bytecodeParser, parser);
	}

	@Override
	protected void processNoErrors(CompilationUnit unit) {
		unit.transformation();
		unit.generateClassfile();
	}

	/**
	 * Check that the output directory given in args exists.
	 */
	@Override
	public int processArgs(String[] args) {
		int result = super.processArgs(args);
		if (result != 0) {
			return result;
		}
		if (program.options().hasValueForOption("-d")) {
			String destDir = program.options().getValueForOption("-d");
			File dir = new File(destDir);
			if (!dir.isDirectory()) {
				System.err.println("Error: output directory not found: " + destDir);
				return EXIT_CONFIG_ERROR;
			}
		}
		return 0;
	}
}
