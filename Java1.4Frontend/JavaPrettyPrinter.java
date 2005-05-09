import AST.*;

import java.util.*;
import java.io.*;
import parser.*;

class JavaPrettyPrinter {

  public static void main(String args[]) {
    Program program = new Program();
    program.addKeyValueOption("-classpath");
    program.addKeyValueOption("-sourcepath");
    program.addKeyValueOption("-bootclasspath");
    program.addKeyValueOption("-extdirs");
    program.addKeyValueOption("-d");
    program.addKeyOption("-verbose");
    
    program.addOptions(args);
    Collection files = program.files();
    
    long initParseTime = System.currentTimeMillis();
    program = new Program();
    JavaParser parser = new JavaParser();
    initParseTime = System.currentTimeMillis() - initParseTime;
    long parseTime = System.currentTimeMillis();
    for(Iterator iter = files.iterator(); iter.hasNext(); ) {
      try {
        String name = (String)iter.next();
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
      } catch (IOException e) {
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
      }
    }
    parseTime = System.currentTimeMillis() - parseTime;
    long rewriteAndUpdateTime = System.currentTimeMillis();
    program.updateRemoteAttributeWrites(files.size());
    rewriteAndUpdateTime = System.currentTimeMillis() - rewriteAndUpdateTime;
    program.prettyPrint(files.size());
    long errorCheckTime = System.currentTimeMillis();
    if(program.errorCheck(files.size())) {
      errorCheckTime = System.currentTimeMillis() - errorCheckTime;
      //long code = System.currentTimeMillis() - start - program.parseTime;
      System.err.println("InitParser: " + initParseTime);
      System.err.println("Parse: " + parseTime);
      System.err.println("ClassFileReadTime: " + program.classFileReadTime);
      System.err.println("String computation: " + (program.stringComputation - program.classFileReadTime));
      System.err.println("Print time: " + program.printTime);
      System.err.println("Error check time: " + errorCheckTime);
      System.err.println("Number of files: " + files.size());
      
      System.exit(1);
      
    }
    errorCheckTime = System.currentTimeMillis() - errorCheckTime;
    //long code = System.currentTimeMillis() - start - program.parseTime;
    System.err.println("InitParser: " + initParseTime);
    System.err.println("Parse: " + parseTime);
    System.err.println("ClassFileReadTime: " + program.classFileReadTime);
    System.err.println("String computation: " + (program.stringComputation - program.classFileReadTime));
    System.err.println("Print time: " + program.printTime);
    System.err.println("Error check time: " + errorCheckTime);
    System.err.println("Number of files: " + files.size());
  }

  
}
