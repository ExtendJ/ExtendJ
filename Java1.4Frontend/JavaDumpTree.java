import AST.*;

import java.io.*;
import parser.*;

class JavaDumpTree {

  public static void main(String args[]) {
    Program program = new Program();
    program = new Program();
    for(int i = 0; i < args.length; i++) {
      try {
        Reader reader = new FileReader(args[i]);
        JavaParser parser = new JavaParser();
        JavaScanner scanner = new JavaScanner(new UnicodeEscapes(new BufferedReader(reader)));
        CompilationUnit unit = ((Program)parser.parse(scanner)).getCompilationUnit(0);
      	reader.close();
        program.addCompilationUnit(unit);
      } catch (IOException e) {
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
      }
    }
    program.prettyPrint(args.length);
    program.printTree(args.length);
    program.errorCheck(args.length);
  }

  
}
