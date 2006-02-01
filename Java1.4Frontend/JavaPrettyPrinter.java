import AST.*;

import java.util.*;
import java.io.*;
import parser.*;

class JavaPrettyPrinter {

  public static void main(String args[]) {
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
      return;
    }
    if(program.hasOption("-help") || files.isEmpty()) {
      printUsage();
      return;
    }
    
    long initParseTime = System.currentTimeMillis();
    program = new Program();
    JavaParser parser = new JavaParser();
    initParseTime = System.currentTimeMillis() - initParseTime;
    long parseTime = System.currentTimeMillis();
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
        
        //long parse = System.currentTimeMillis();
        program.addCompilationUnit(unit);
        //program.parseTime += System.currentTimeMillis() - parse;
      } catch (Error e) {
        if(e.getMessage() != null) {
          System.err.println(name + ": " + e.getMessage());
          System.exit(1);
        }
        e.printStackTrace();
        System.exit(1);
      } catch (RuntimeException e) {
        if(e.getMessage() != null) {
          System.err.println(name + ": " + e.getMessage());
          System.exit(1);
        }
        e.printStackTrace();
        System.exit(1);
      } catch (IOException e) {
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    parseTime = System.currentTimeMillis() - parseTime;
    long rewriteAndUpdateTime = System.currentTimeMillis();
    if(Program.verbose())
      System.out.println("Updating remote attribute collections");
    program.updateRemoteAttributeCollections(files.size());
    rewriteAndUpdateTime = System.currentTimeMillis() - rewriteAndUpdateTime;
    if(Program.verbose())
      System.out.println("Pretty printing source code");
    program.prettyPrint(files.size());
    long errorCheckTime = System.currentTimeMillis();
    if(Program.verbose())
      System.out.println("Error checking source code");
    if(program.errorCheck(files.size())) {
      errorCheckTime = System.currentTimeMillis() - errorCheckTime;
      //long code = System.currentTimeMillis() - start - program.parseTime;
      /*
      System.err.println("InitParser: " + initParseTime);
      System.err.println("Parse: " + parseTime);
      System.err.println("ClassFileReadTime: " + program.classFileReadTime);
      System.err.println("String computation: " + (program.stringComputation - program.classFileReadTime));
      System.err.println("Print time: " + program.printTime);
      System.err.println("Error check time: " + errorCheckTime);
      System.err.println("Number of files: " + files.size());
      */
      System.exit(1);
      
    }
    errorCheckTime = System.currentTimeMillis() - errorCheckTime;
    //long code = System.currentTimeMillis() - start - program.parseTime;
    /*
    System.err.println("InitParser: " + initParseTime);
    System.err.println("Parse: " + parseTime);
    System.err.println("ClassFileReadTime: " + program.classFileReadTime);
    System.err.println("String computation: " + (program.stringComputation - program.classFileReadTime));
    System.err.println("Print time: " + program.printTime);
    System.err.println("Error check time: " + errorCheckTime);
    System.err.println("Number of files: " + files.size());
    */
  }


  protected static void printUsage() {
    printVersion();
    System.out.println(
      "\nJavaPrettyPrinter\n\n" +
      "Usage: java JavaPrettyPrinter <options> <source files>\n" +
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
    System.out.println("Java1.4Frontend (http://jastadd.cs.lth.se) Version R20060127");
  }
}
