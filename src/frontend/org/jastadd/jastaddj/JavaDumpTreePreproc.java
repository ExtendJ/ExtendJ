package org.jastadd.jastaddj;
/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 *
 * Copyright (c) 2005-2008, Torbjorn Ekman
 *		      2011, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 */

import AST.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.*;

/**
 * Dump the parsed AST for a Java program.
 */
class JavaDumpTreePreproc extends Frontend {
	public static void main(String args[]) {
		String[] preprocess_input = new String[2];
		preprocess_input[0] = args[0];
		preprocess_input[1] = args[0].substring(0, args[0].length() - 5) + "pre.java";;
		preprocess(preprocess_input);
		args[0] = preprocess_input[1];
		compile(args);
		File file = new File(preprocess_input[1]);
		file.delete();
	}

	public static boolean compile(String args[]) {
		return new JavaDumpTreePreproc().process(
				args,
				new BytecodeParser(),
				new JavaParser() {
					@Override
					public CompilationUnit parse(java.io.InputStream is, String fileName)
							throws java.io.IOException, beaver.Parser.Exception {
						return new parser.JavaParser().parse(is, fileName);
					}
				}
				);
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

	@Override
	protected String name() {
		return "Java AST Dumper";
	}

	@Override
	protected String version() {
		return JastAddJVersion.getVersion();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private static final String REFERENCE_SEPARATOR = ":REF:";
	private static int current_pos;
	
	private static void preprocess(String[] args) {
		StringBuilder text = new StringBuilder();
		int character = 0;
		int i;
		int ref_index = 0;
		ArrayList<Integer> insert_indexes = new ArrayList<Integer>();
		Reader f = null;
		BufferedWriter writer = null;
		
		if(args.length != 2) {
			System.out.println("Error, incorrect input arguments");
			System.exit(1);
		}
		try {
			f = new BufferedReader(new FileReader(args[0]));
		} catch(Exception e) {
			System.out.println("Error, wrong input file path");
			System.exit(1);
		}



		try {
			character = f.read();
			while(character != -1) {
				text.append((char)character);
				character = f.read();
			}
			f.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(text.length() < 2) 
			return;


		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf-8"));
		    
		} catch (IOException ex) {
			System.out.println("Error, wrong output file path");
			System.exit(1);
		} 

		locate_generic_method_references(text, insert_indexes);

		for(i = 0; i < text.length(); i++) {
			if(ref_index != insert_indexes.size() && i == insert_indexes.get(ref_index)) {
				try {
					writer.write(REFERENCE_SEPARATOR);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ref_index++;
			}
			try {
				writer.write(text.charAt(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}

	private static int locate_last_nonspace(StringBuilder text, int start_pos)
	{
		int pos;
		if(start_pos == 0) {
			return start_pos;
		}
		pos = start_pos - 1;
		while(Character.isWhitespace(text.charAt(pos)) && pos != 0) {
			pos--;
		}
		
		return pos;
	}

	static void locate_array_start(StringBuilder text) {
		if(current_pos == 0) {
			return;
		}

		current_pos = current_pos - 1;
		while(text.charAt(current_pos) != '[' && current_pos != 0) {
			current_pos = current_pos - 1;
		}

		return;	
	}

	static void locate_generic_start(StringBuilder text) 
	{
		if(current_pos == 0)
			return;
		while(true) {
			current_pos = current_pos - 1;
			if(current_pos == 0) 
				return;
			else if(text.charAt(current_pos) == '<')
				return;
			else if(text.charAt(current_pos) == '>') 
				locate_generic_start(text);
			
		}
	}

	static void locate_generic_method_references(StringBuilder text, ArrayList<Integer> insert_indexes)
	{	
		for(int i = 0; i < text.length() - 2; i++) {
			if(text.charAt(i) == ':' && text.charAt(i+1) == ':' && text.charAt(i+2) != ':') {
				current_pos = locate_last_nonspace(text, i);
				while(text.charAt(current_pos) == ']') {
					locate_array_start(text);
					if(current_pos == 0)
						return;
					current_pos = locate_last_nonspace(text, current_pos);
				}

				if(text.charAt(current_pos) == '>') {
					locate_generic_start(text);
					if(current_pos == 0)
						return;
					else if(text.charAt(current_pos) == '<') {
						insert_indexes.add(current_pos);
					}
					
				}

			}

		}
	}
	
	
}
