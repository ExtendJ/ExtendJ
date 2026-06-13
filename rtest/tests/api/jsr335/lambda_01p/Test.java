// Test that the method of an implicit anonymous class generated for a lambda
// expression has the expected method type signature.
// https://bitbucket.org/extendj/extendj/issues/286/strange-bytecode-generated-for-anonymous
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.ConstructorDecl;
import org.extendj.ast.Attribute;
import org.extendj.ast.Dot;
import org.extendj.ast.MethodAccess;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.ExprStmt;
import org.extendj.ast.LambdaExpr;
import org.extendj.ast.SignatureAttribute;
import org.extendj.ast.BodyDecl;
import org.extendj.ast.TypeDecl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static runtime.Test.testTrue;
import static runtime.Test.testFalse;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) throws IOException {
    String code = "import java.util.*;"
        + "public class Test {"
        + "  void m1(List<? extends String> names) {"
        + "    names.stream().mapToInt(name -> name.length());"
        + "  }"
        + "}";
    CompilationUnit cu = parseCompilationUnit(code);
    MethodDecl m1 = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
    Dot dot1 = (Dot) ((ExprStmt) m1.getBlock().getStmt(0)).getExpr();
    Dot dot2 = (Dot) dot1.getRight();
    MethodAccess mapToInt = (MethodAccess) dot2.getRight();
    LambdaExpr lambda = (LambdaExpr) mapToInt.getArg(0);
    TypeDecl anonType = lambda.toClass().type();
    for (BodyDecl decl : anonType.getBodyDeclList()) {
      if (decl instanceof MethodDecl) {
        MethodDecl method = (MethodDecl) decl;
        String typeSignature = method.methodTypeSignature();
        System.out.format("Type signature of %s: %s%n", method.name(), typeSignature);
      }
    }
  }

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

