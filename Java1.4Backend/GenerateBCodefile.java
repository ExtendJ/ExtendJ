import AST.*;

import java.io.*;
import parser.JavaCompiler;

class GenerateBCodefile {

  public static void main(String args[]) {
    // Create a parser which reads from standard input
    Program program = new Program();

    long start = System.currentTimeMillis();
    program = new Program();
    program.isFinal = true;
    for(int i = 0; i < args.length; i++) {
      try {
        CompilationUnit unit = JavaCompiler.parse(args[i]);
        program.addCompilationUnit(unit);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return;
      } catch (IOException e) {
        System.err.println(e);
        e.printStackTrace();
        return;	
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
        return;
      }
    }
    program.prettyPrint(args.length);
    program.errorCheck(args.length);
    program.prettyPrintBCode(System.out, args.length);
    program.generateClassfile(args.length);
  }
}
