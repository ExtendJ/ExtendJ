import AST.*;

class JavaPrettyPrinter extends Frontend {
  public static void main(String args[]) {
    if(!compile(args))
      System.exit(1);
  }

  public static boolean compile(String args[]) {
    return new JavaPrettyPrinter().process(
        args,
        new BytecodeParser(),
        new JavaParser() {
          public CompilationUnit parse(java.io.InputStream is, String fileName) throws java.io.IOException, beaver.Parser.Exception {
            return new parser.JavaParser().parse(is, fileName);
          }
        }
    );
  }
  protected void processErrors(java.util.Collection errors, CompilationUnit unit) {
    super.processErrors(errors, unit);
    unit.dumpTreeNoRewrite();
  }
  protected void processNoErrors(CompilationUnit unit) {
    System.out.println(unit.toString());
  }

  protected String name() { return "Java1.4Frontend + Backend + Java5Extensions Dumptree"; }
  protected String version() { return "R20070504"; }
}
