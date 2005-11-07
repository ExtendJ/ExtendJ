import AST.*;

import java.util.*;
import java.io.*;
import parser.*;

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
    
    JavaParser parser = new JavaParser();
    for(Iterator iter = files.iterator(); iter.hasNext(); ) {
      String name = (String)iter.next();
      try {
        Reader reader = new FileReader(name);
        JavaScanner scanner = new JavaScanner(new UnicodeEscapes(new BufferedReader(reader)));
        CompilationUnit unit = ((Program)parser.parse(scanner)).getCompilationUnit(0);
        unit.setFromSource(true);
        unit.setRelativeName(name);
        unit.setPathName(".");
      	reader.close();
        program.addCompilationUnit(unit);
      } catch (Error e) {
        System.out.println(name + ": " + e.getMessage());
        return false;
      } catch (RuntimeException e) {
        System.out.println(name + ": " + e.getMessage());
        return false;
      } catch (IOException e) {
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
      }
    }
    program.updateRemoteAttributeCollections(files.size());
    if(Program.verbose())
      program.prettyPrint(files.size());
    if(Program.verbose())
      System.out.println("Begin error checking");
    if(!program.errorCheck(files.size())) {
      if(Program.verbose())
        System.out.println("Generating class files");
      program.java2Transformation(files.size());
      program.generateClassfile(files.size());
      return true;
    }
    else {
      return false;
    }
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
    System.out.println("Java1.4Frontend + Backend (http://jastadd.cs.lth.se) Version R20050930");
  }

}
