// Test that the type signature referring to an inner class is correct.
// https://bitbucket.org/extendj/extendj/issues/294/incorrect-type-signatures-for-inner
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.Attribute;
import org.extendj.ast.SignatureAttribute;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static runtime.Test.testEquals;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) throws IOException {
    String code =
        "package the.library;"
        + "public class Jac2<T> {"
        + "  Jac2<Inner> x;"
        + "  static class Inner { }"
        + "}";
    CompilationUnit cu = parseCompilationUnit(code);
    FieldDecl field = (FieldDecl) cu.getTypeDecl(0).getBodyDecl(0);

    String signature = field.getDeclarator(0).type().fieldTypeSignature();
    testEquals("Lthe/library/Jac2<Lthe/library/Jac2$Inner;>;", signature);
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

