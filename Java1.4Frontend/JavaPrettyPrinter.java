import AST.*;

import java.io.*;
import parser.*;

class JavaPrettyPrinter {

  public static void main(String args[]) {
    String[] files = args;
    if(files.length == 1 && new File(files[0]).isDirectory()) {
      String dirName = files[0];
      if(!dirName.endsWith("/"))
        dirName += "/";
      File file = new File(dirName);
      files = file.list(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.endsWith(".java");
        }
      });
      for(int i = 0; i < files.length; i++) {
        files[i] = dirName + files[i];
      }
    }
    Program program = new Program();
    long initParseTime = System.currentTimeMillis();
    program = new Program();
    JavaParser parser = new JavaParser();
    initParseTime = System.currentTimeMillis() - initParseTime;
    long parseTime = System.currentTimeMillis();
    for(int i = 0; i < files.length; i++) {
      try {
        Reader reader = new FileReader(files[i]);
        JavaScanner scanner = new JavaScanner(new UnicodeEscapes(new BufferedReader(reader)));
        CompilationUnit unit = ((Program)parser.parse(scanner)).getCompilationUnit(0);
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
    program.prettyPrint(files.length);
    long errorCheckTime = System.currentTimeMillis();
    if(program.errorCheck(files.length)) {
      errorCheckTime = System.currentTimeMillis() - errorCheckTime;
      //long code = System.currentTimeMillis() - start - program.parseTime;
      System.err.println("InitParser: " + initParseTime);
      System.err.println("Parse: " + parseTime);
      System.err.println("ClassFileReadTime: " + program.classFileReadTime);
      System.err.println("String computation: " + (program.stringComputation - program.classFileReadTime));
      System.err.println("Print time: " + program.printTime);
      System.err.println("Error check time: " + errorCheckTime);
      System.err.println("Number of files: " + files.length);
      
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
    System.err.println("Number of files: " + files.length);
  }

  
}
