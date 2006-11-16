import AST.*;
import java.util.*;
import java.io.*;

public abstract class Frontend {
  protected Program program;

  protected Frontend() {
    program = new Program();
  }

  protected void initReaders() {
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

  }
  protected void initOptions() {
    program.initOptions();
    program.addKeyValueOption("-classpath");
    program.addKeyValueOption("-sourcepath");
    program.addKeyValueOption("-bootclasspath");
    program.addKeyValueOption("-extdirs");
    program.addKeyValueOption("-d");
    program.addKeyOption("-verbose");
    program.addKeyOption("-version");
    program.addKeyOption("-help");
  }
  protected void processArgs(String[] args) {
    program.addOptions(args);
  }

  protected void printUsage() {
    printVersion();
    System.out.println(
      "\n" + name() + "\n\n" +
      "Usage: java " + name() + " <options> <source files>\n" +
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

  protected void printVersion() {
    System.out.println(name() + " (http://jastadd.cs.lth.se) Version " + version());
  }

  protected String name() {
    return "Java1.4Frontend";
  }
  protected String version() {
    return "R20060915";
  }
}
