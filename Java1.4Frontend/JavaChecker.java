import AST.*;

class JavaChecker extends Frontend {

  public static void main(String args[]) {
    compile(args);
  }

  public static boolean compile(String args[]) {
    return new JavaChecker().process(
        args,
        new bytecode.Parser(),
        new JavaParser() {
          parser.JavaParser parser = new parser.JavaParser();
          public CompilationUnit parse(java.io.InputStream is, String fileName) throws java.io.IOException, beaver.Parser.Exception {
            return parser.parse(is, fileName);
          }
        }
    );
  }

  protected String name() { return "JavaChecker"; }
  protected String version() { return "R20060915"; }
}
