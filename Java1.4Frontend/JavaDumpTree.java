import AST.*;

import java.io.*;

class JavaDumpTree {

  public static void main(String args[]) {
    Program program = new Program();
    program = new Program();
    for(int i = 0; i < args.length; i++) {
      try {
        CompilationUnit unit = new ClassFile(args[i]).getCompilationUnit();
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
