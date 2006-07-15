package bytecode;

import AST.*;
import java.io.*;

class ByteCodeParser {
	public static void main(String[] args) {
		Program program = new Program();
		for (int i = 0; i < args.length; i++) {
			System.out.println("Processing: " + args[i]);
			try {
				Parser p = new Parser(args[i]);
				program.addCompilationUnit(p.parse(null, null, program));

			} catch (FileNotFoundException e) {
				System.out.println("Error: " + args[i] + " not found");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		System.out.println("Result:");
    System.out.println(program);
	}
}
