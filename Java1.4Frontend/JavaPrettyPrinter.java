import AST.*;

import java.util.*;
import java.io.*;

class JavaPrettyPrinter extends Frontend {
  public static void main(String args[]) {
    if(!compile(args))
      System.exit(1);
  }

  public static boolean compile(String args[]) {
    return new JavaPrettyPrinter().process(
        args,
        new bytecode.Parser(),
        new JavaParser() {
          public CompilationUnit parse(InputStream is, String fileName) throws IOException, beaver.Parser.Exception {
            return new parser.JavaParser().parse(is, fileName);
          }
        }
    );
  }

  protected void processErrors(Collection errors, CompilationUnit unit) {
    super.processErrors(errors, unit);
    System.out.println(unit);
  }
  protected void processNoErrors(CompilationUnit unit) {
    System.out.println(unit);
  }

  protected String name() { return "JavaPrettyPrinter"; }
  protected String version() { return "R20060915"; }
}
