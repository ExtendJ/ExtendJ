import AST.*;

import java.util.*;
import java.io.*;

class JavaPrettyPrinter extends Frontend {
  public static void main(String args[]) {
    if(!compile(args))
      System.exit(1);
  }

  public static boolean compile(String args[]) {
    return new JavaPrettyPrinter().process(args);
  }

  public boolean process(String args[]) {
    initReaders();
    initOptions();
    processArgs(args);

    Collection files = program.files();

    if(program.hasOption("-version")) {
      printVersion();
      return false;
    }
    if(program.hasOption("-help") || files.isEmpty()) {
      printUsage();
      return false;
    }

    for(Iterator iter = files.iterator(); iter.hasNext(); ) {
      String name = (String)iter.next();
      program.addSourceFile(name);
    }

    try {
      for(Iterator iter = program.compilationUnitIterator(); iter.hasNext(); ) {
        CompilationUnit unit = (CompilationUnit)iter.next();
        if(unit.fromSource()) {
          Collection errors = new LinkedList();
          unit.errorCheck(errors);
          if(!errors.isEmpty()) {
            System.out.println("Errors:");
            for(Iterator iter2 = errors.iterator(); iter2.hasNext(); ) {
              String s = (String)iter2.next();
              System.out.println(s);
            }
            return false;
          }
        }
      }
    } catch (ParseError e) {
      System.err.println(e.getMessage());
      return false;
    } catch (LexicalError e) {
      System.err.println(e.getMessage());
      return false;
    } catch (Error e) {
      System.err.println(e.getMessage());
      throw e;
      //return false;
    }

    if(Program.verbose())
      System.out.println("Pretty printing source code");
    System.out.println(program.toString());
    return true;
  }

  protected String name() { return "JavaPrettyPrinter"; }
  protected String version() { return "R20060915"; }
}
