import AST.*;

import java.io.*;
import parser.*;

class GenerateBCodefile {

  public static void main(String args[]) {
    Program program = new Program();

    long start = System.currentTimeMillis();
    program = new Program();
    for(int i = 0; i < args.length; i++) {
      try {
        Reader reader = new FileReader(args[i]);
        JavaParser parser = new JavaParser();
        JavaScanner scanner = new JavaScanner(new UnicodeEscapes(new BufferedReader(reader)));
        CompilationUnit unit = ((Program)parser.parse(scanner)).getCompilationUnit(0);
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
