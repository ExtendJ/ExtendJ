/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 *
 * Copyright (c) 2005-2008, Torbjorn Ekman
 *               2011-2014, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 */
package org.jastadd.extendj;

import org.jastadd.extendj.ast.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.*;

/**
 * Dump the parsed AST for some Java source files.
 */
class JavaDumpFrontendErrors extends Frontend {

	/**
	 * Entry point.
	 * @param args command-line arguments
	 */
	public static void main(String args[]) {
		int exitCode = new JavaDumpFrontendErrors().run(args);
		if (exitCode != 0) {
			System.exit(exitCode);
		}
	}

	
	private final JavaParser parser;
	private final BytecodeReader bytecodeParser;

	/**
	 * Initialize the compiler.
	 */
	public JavaDumpFrontendErrors() {
		super("Java AST Dumper", ExtendJVersion.getVersion());
		parser = new JavaParser() {
			@Override
			public CompilationUnit parse(InputStream is, String fileName) throws IOException,
					beaver.Parser.Exception {
				return new org.jastadd.extendj.parser.JavaParser().parse(is, fileName);
			}
		};
		bytecodeParser = new BytecodeReader() {
			@Override
			public CompilationUnit read(InputStream is, String fullName, Program p)
					throws FileNotFoundException, IOException {
				return new BytecodeParser(is, fullName).parse(null, null, p);
			}
		};
	}

	/**
	 * @param args command-line arguments
	 * @return {@code true} on success, {@code false} on error
	 * @deprecated Use run instead!
	 */
	@Deprecated
	public static boolean compile(String args[]) {
		return 0 == new JavaDumpFrontendErrors().run(args);
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
		System.out.println("Errors:");
		for (Iterator iter2 = errors.iterator(); iter2.hasNext(); ) {
			System.out.println(iter2.next());
			}
	}

	@Override
	protected void processNoErrors(CompilationUnit unit) {
		
	}
}
