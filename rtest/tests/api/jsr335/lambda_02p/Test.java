// Test that a lambda targeting a wildcard-parameterized functional interface
// gets the non-wildcard target type as its type (JLS SE8 §9.9).
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.ASTNode;
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.JavaParser;
import org.extendj.ast.LambdaExpr;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;

import java.io.ByteArrayInputStream;

import static runtime.Test.testEqual;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    String code = "import java.util.function.Function;\n"
        + "public class Test {\n"
        + "  Function<? super String, Integer> fun = s -> s.length();\n"
        + "}\n";
    CompilationUnit cu = parseCompilationUnit(code);
    LambdaExpr lambda = findLambda(cu);
    if (lambda == null) {
      fail("no lambda expression found in test code");
    }
    // The lambda type must be the non-wildcard parameterization
    // Function<String, Integer>, not the wildcard target type
    // Function<? super String, Integer>.
    testEqual("java.util.function.Function<java.lang.String, java.lang.Integer>",
        lambda.type().typeName());
  }

  /**
   * Find the first LambdaExpr in the subtree rooted at the given node.
   */
  static LambdaExpr findLambda(ASTNode<?> node) {
    if (node instanceof LambdaExpr) {
      return (LambdaExpr) node;
    }
    for (int i = 0; i < node.getNumChildNoTransform(); i++) {
      ASTNode<?> child = node.getChildNoTransform(i);
      if (child != null) {
        LambdaExpr result = findLambda(child);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
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
