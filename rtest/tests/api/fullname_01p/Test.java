// Test the fullName() attribute evaluated on a TypeVariable.
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericMethodDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeVariable;

import java.io.ByteArrayInputStream;

import static runtime.Test.testEqual;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    String code = "package p;\n"
        + "interface Comparator<T> {"
        + "  <U> Comparator<T> thenComparing(Comparator<? super U> b);\n"
        + "}\n";
    CompilationUnit cu = parseCompilationUnit(code);
    GenericMethodDecl method = (GenericMethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
    TypeVariable var = method.getTypeParameter(0);
    testEqual("thenComparing(p.Comparator)", method.signature());
    testEqual("p.Comparator.thenComparing(p.Comparator)@U", var.fullName());
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
