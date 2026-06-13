// The MethodDecl.isFinal() attribute should be false for methods that don't
// use the final modifier.
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeVariable;

import java.io.ByteArrayInputStream;

import static runtime.Test.fail;
import static runtime.Test.testFalse;
import static runtime.Test.testTrue;

public class Test {
  public static void main(String[] args) {
    // A non-final private method in a non-final class is treated as non-final.
    String code = "class A {\n"
        + "  private void foo() {}\n"
        + "}\n";
    CompilationUnit cu = parseCompilationUnit(code);
    MethodDecl method = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
    testTrue(method.isPrivate());
    testFalse(method.hostType().isFinal());
    testFalse(method.isFinal());

    // A non-final method in a final class is treated as non-final.
    code = "final class B {\n"
        + "  void foo() {}\n"
        + "}\n";
    cu = parseCompilationUnit(code);
    method = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
    testFalse(method.isPrivate());
    testTrue(method.hostType().isFinal());
    testFalse(method.isFinal());

    // A final method in a non-final class is treated as final.
    code = "class B {\n"
        + "  final void foo() {}\n"
        + "}\n";
    cu = parseCompilationUnit(code);
    method = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
    testFalse(method.isPrivate());
    testFalse(method.hostType().isFinal());
    testTrue(method.isFinal());
  }

  /**
   * Parse a compilation unit.
   */
  static CompilationUnit parseCompilationUnit(String code) {
    try {
      Program program = new Program();
      program.initBytecodeReader(Program.defaultBytecodeReader());
      JavaParser sourceParser = Program.defaultJavaParser();
      program.initJavaParser(sourceParser);
      CompilationUnit unit = sourceParser.parse(
          new ByteArrayInputStream(code.getBytes("UTF-8")), "<no path>");
      program.addCompilationUnit(unit);
      unit = program.getCompilationUnit(0);
      unit.setClassSource(new FileClassSource(new SourceFolderPath("<no source>"), "<no path>"));
      unit.setFromSource(true);
      return unit;
    } catch (Exception e) {
      e.printStackTrace();
      fail("failed to parse test code");
    }
    // Failed.
    return null;
  }
}
