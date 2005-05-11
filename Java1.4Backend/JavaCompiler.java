import AST.*;

import java.util.*;
import java.io.*;
import parser.*;

class JavaCompiler {

  public static void main(String args[]) {
    Program program = new Program();
    program.addOptionDescription("-classpath", true);
    program.addOptionDescription("-sourcepath", true);
    program.addOptionDescription("-bootclasspath", true);
    program.addOptionDescription("-extdirs", true);
    program.addOptionDescription("-d", true);
    program.addOptionDescription("-verbose", false);
    
    program.addOptions(args);
    Collection files = program.files();
    
    JavaParser parser = new JavaParser();
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
    program.updateRemoteAttributeCollections(files.size());
    if(!program.errorCheck(files.size())) {
      program.generateClassfile(files.size());
    }
    else {
      System.exit(1);
    }
  }
}
