import AST.*;

import java.util.*;
import java.io.*;
import beaver.Symbol;

class JavaCompiler {

  public static void main(String args[]) {
    if(!compile(args))
      System.exit(1);
  }
  
  public static boolean compile(String args[]) {
    Program program = new Program();

    program.initBytecodeReader(new bytecode.Parser());
    program.initJavaParser(
      new JavaParser() {
        public CompilationUnit parse(InputStream is, String fileName) throws IOException, beaver.Parser.Exception {
          return new parser.JavaParser().parse(is, fileName);
        }
      }
    );
    // extract package name from a source file without parsing the entire file
    program.initPackageExtractor(new parser.JavaScanner());

    program.initOptions();    
    program.addKeyOption("-version");
    program.addKeyOption("-print");
    program.addKeyOption("-g");
    program.addKeyOption("-g:none");
    program.addKeyOption("-g:lines,vars,source");
    program.addKeyOption("-nowarn");
    program.addKeyOption("-verbose");
    program.addKeyOption("-deprecation");
    program.addKeyValueOption("-classpath");
    program.addKeyValueOption("-sourcepath");
    program.addKeyValueOption("-bootclasspath");
    program.addKeyValueOption("-extdirs");
    program.addKeyValueOption("-d");
    program.addKeyValueOption("-encoding");
    program.addKeyValueOption("-source");
    program.addKeyValueOption("-target");
    program.addKeyOption("-help");
    program.addKeyOption("-O");
    program.addKeyOption("-J-Xmx128M");
    
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
    
    printVersion();
    try {
      for(Iterator iter = files.iterator(); iter.hasNext(); ) {
        String name = (String)iter.next();
        program.addSourceFile(name);
      }

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
            if(Program.hasOption("-print")) System.out.println(unit);
            unit.generateClassfile();
          }
        }
      }
    } catch (ParseError e) {
      System.err.println(e.getMessage());
      return false;
    } catch (LexicalError e) {
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
    System.out.println("Java1.4Frontend + Backend (http://jastadd.cs.lth.se) Version R20070504");
  }

}
