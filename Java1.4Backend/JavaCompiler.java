import AST.*;

import java.util.*;
import java.io.*;
import parser.*;
import beaver.Symbol;

class JavaCompiler {

  public static void main(String args[]) {
    if(!compile(args))
      System.exit(1);
  }
  
  public static boolean compile(String args[]) {
    Program program = new Program();
    program.initOptions();    
    program.addKeyValueOption("-classpath");
    program.addKeyValueOption("-sourcepath");
    program.addKeyValueOption("-bootclasspath");
    program.addKeyValueOption("-extdirs");
    program.addKeyValueOption("-d");
    program.addKeyOption("-verbose");
    program.addKeyOption("-version");
    program.addKeyOption("-help");
    program.addKeyOption("-g");
    
    program.addOptions(args);
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
          if(Program.verbose())
            System.out.println("Error checking " + unit.relativeName());
          long time = System.currentTimeMillis();
          unit.errorCheck(errors);
          time = System.currentTimeMillis()-time;
          if(Program.verbose())
            System.out.println("Error checking " + unit.relativeName() + " done in " + time + " ms");
          if(!errors.isEmpty()) {
            System.out.println("Errors:");
            for(Iterator iter2 = errors.iterator(); iter2.hasNext(); ) {
              String s = (String)iter2.next();
              System.out.println(s);
            }
            return false;
          }
          else {
            unit.java2Transformation();
            unit.generateClassfile();
          }
        }
      }
    } catch (JavaParser.SourceError e) {
      System.err.println(e.getMessage());
      return false;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    return true;
  }

  protected static void printUsage() {
    printVersion();
    System.out.println(
      "\nJavaCompiler\n\n" +
      "Usage: java JavaCompiler <options> <source files>\n" +
      "  -verbose                  Output messages about what the compiler is doing\n" +
      "  -classpath <path>         Specify where to find user class files\n" +
      "  -sourcepath <path>        Specify where to find input source files\n" + 
      "  -bootclasspath <path>     Override location of bootstrap class files\n" + 
      "  -extdirs <dirs>           Override location of installed extensions\n" +
      "  -d <directory>            Specify where to place generated class files\n" +
      "  -help                     Print a synopsis of standard options\n" +
      "  -version                  Print version information\n"
    );
  }

  protected static void printVersion() {
    System.out.println("Java1.4Frontend + Backend (http://jastadd.cs.lth.se) Version R20060915");
  }

}
