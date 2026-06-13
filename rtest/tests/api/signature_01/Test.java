// Test that a type signature is generated for a constructor declaration
// that uses generic type variables.
// https://bitbucket.org/extendj/extendj/issues/269/missing-type-variables-in-bytecode
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.ConstructorDecl;
import org.extendj.ast.Attribute;
import org.extendj.ast.SignatureAttribute;

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
    String code = "public class Jac1<T> {"
        + "  public Jac1(T t, T[] ta) { }"
        + "}";
    CompilationUnit cu = parseCompilationUnit(code);
    ConstructorDecl main = (ConstructorDecl) cu.getTypeDecl(0).getBodyDecl(0);

    // Generate the bytecode and assert that there are no checkcast instructions.
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bos);
    for (Attribute attr : main.attributes()) {
      if (attr instanceof SignatureAttribute) {
        return;
      }
    }
    System.err.println("Constructor is missing a signature attribute!");
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

