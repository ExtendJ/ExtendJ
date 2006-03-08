import AST.*;

import java.util.*;
import java.io.*;
import parser.*;

class JavaCompiler {

  public static void main(String args[]) {
    if(!compileLazy(args))
      System.exit(1);
  }
  
  public static boolean compileLazy(String args[]) {
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
    
    JavaParser parser = new JavaParser();
    /*
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
        System.err.println(name + ": " + e.getMessage());
        return false;
      } catch (RuntimeException e) {
        System.err.println(name + ": " + e.getMessage());
        return false;
      } catch (IOException e) {
        System.err.println("error: " + e.getMessage());
        return false;
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
        return false;
      }
    }
    */
    for(Iterator iter = files.iterator(); iter.hasNext(); ) {
      String name = (String)iter.next();
      ClassFile.addSourceFile(name);
    }

    int unitIndex = 0;
    for(Iterator iter = files.iterator(); iter.hasNext(); ) {
      String name = (String)iter.next();
      if(!ClassFile.processedSourceFile(name)) {
        try {
          Reader reader = new FileReader(name);
          JavaScanner scanner = new JavaScanner(new UnicodeEscapes(new BufferedReader(reader)));
          
          CompilationUnit unit = ((Program)parser.parse(scanner)).getCompilationUnit(0);
          unit.setFromSource(true);
          unit.setRelativeName(name);
          unit.setPathName(".");
        	reader.close();
          program.addCompilationUnit(unit);
          program.getCompilationUnit(program.getNumCompilationUnit()-1);
        } catch (Error e) {
          System.err.println(name + ": " + e.getMessage());
          return false;
        } catch (RuntimeException e) {
          System.err.println(name + ": " + e.getMessage());
          return false;
        } catch (IOException e) {
          System.err.println("error: " + e.getMessage());
          return false;
        } catch (Exception e) {
          System.err.println(e);
          e.printStackTrace();
          return false;
        }
      }
      while(unitIndex < program.getNumCompilationUnit()) {
        CompilationUnit unit = program.getCompilationUnit(unitIndex++);
        if(unit.fromSource()) {
          unit.updateRemoteAttributeCollections();
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
            //if((unitIndex & 0x3f) == 0)
            //  System.gc();
          }
        }
      }
    }
    AST.SimpleMap.mapStats();
    //System.gc();
    return true;
    /*
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
    */
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
        System.err.println(name + ": " + e.getMessage());
        return false;
      } catch (RuntimeException e) {
        System.err.println(name + ": " + e.getMessage());
        return false;
      } catch (IOException e) {
        System.err.println("error: " + e.getMessage());
        return false;
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
        return false;
      }
    }
    int unitIndex = 0;
    while(unitIndex < program.getNumCompilationUnit()) {
      CompilationUnit unit = program.getCompilationUnit(unitIndex++);
      if(unit.fromSource()) {
        unit.updateRemoteAttributeCollections();
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
    System.out.println("Java1.4Frontend + Backend (http://jastadd.cs.lth.se) Version R20060127");
  }

}
