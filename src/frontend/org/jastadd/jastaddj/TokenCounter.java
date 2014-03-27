/* Copyright (c) 2013, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Lund University nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jastadd.jastaddj;

import scanner.JavaScanner;
import scanner.Unicode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;

import beaver.Symbol;

import parser.JavaParser.Terminals;

/**
 * Counts Java tokens in the files listed on the command line.  Excludes
 * whitespace, comments, curly braces, and parenthesis. The input files need
 * not be pure Java files. Each unexpected character increases the token count
 * by one.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class TokenCounter {
	/**
	 * Count tokens in some Java source files.
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("No arguments given!");
			printHelp();
			System.exit(1);
		}
		for (String arg: args) {
			if (arg.equals("-h")) {
				printHelp();
				System.exit(0);
			}
		}
		int numTokens = 0;
		for (String arg: args) {
			if (arg.startsWith("@")) {
				numTokens += processFileList(arg.substring(1));
			} else {
				numTokens += process(arg);
			}
		}
		System.out.println("tokens: " + numTokens);
	}

	/**
	 * Print help
	 */
	public static void printHelp() {
		System.out.println("Usage: TokenCounter <Java files> [@filelist]");
		System.out.println();
		System.out.println("Counts Java tokens in the files listed on the command line.");
		System.out.println("Excludes whitespace, comments, curly braces, and parenthesis.");
		System.out.println("The input files need not be pure Java files. Each unexpected");
		System.out.println("character increases the total token count by one.");
	}

	/**
	 * @param filename
	 * @return number of tokens in the files listed in the file list
	 */
	public static int processFileList(String filename) {
		int numTokens = 0;
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				numTokens += process(scanner.nextLine());
			}
		} catch (IOException e) {
			System.err.println("Could not read file list: " + filename);
		}
		return numTokens;
	}

	/**
	 * @param filename
	 * @return number of tokens in the file
	 */
	public static int process(String filename) {
		int numTokens = 0;
		File file = new File(filename);
		if (!file.isFile()) {
			System.err.println("Warning: could not open file " + filename);
		} else {
			try {
				FileInputStream is = new FileInputStream(file);
				JavaScanner scanner = new JavaScanner(new Unicode(is));
				while (true) {
					try {
						Symbol next = scanner.nextToken();
						int id = next.getId();
						if (id == Terminals.EOF) {
							break;
						}
						switch (id) {
							case Terminals.LPAREN:
							case Terminals.RPAREN:
							case Terminals.LBRACE:
							case Terminals.RBRACE:
								// not counted!
								break;
							default:
								numTokens += 1;
						}
					} catch (beaver.Scanner.Exception e) {
						System.err.println("Warning "+filename+":"+e.line+":"+e.column+": " + e.getMessage());
						numTokens += 1;
					}
				}
				is.close();
			} catch (IOException e) {
				System.err.println("Warning: could not count tokens of " + filename);
				System.err.println(e.getMessage());
			}
		}
		return numTokens;
	}
}
