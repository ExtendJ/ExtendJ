// Test that an annotations attribute is not generated for a class
// annotation with source retention policy.
// https://bitbucket.org/extendj/extendj/issues/281/annotations-with-source-retention-are
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.Attribute;
import org.extendj.ast.AnnotationsAttribute;

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
    String code = "import java.lang.annotation.*;"
        + "@Trait(\"magical\") public class An6 {"
        + "  public static void main(String[] args) {"
        + "  }"
        + "}"
        + "@Retention(RetentionPolicy.SOURCE)"
        + "@interface Trait {"
        + "  String value() default \"\";"
        + "}";

    CompilationUnit cu = parseCompilationUnit(code);
    TypeDecl an6 = cu.getTypeDecl(0);

    // Generate the bytecode and assert that there are no checkcast instructions.
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bos);
    for (Attribute attr : an6.attributes()) {
      if (attr instanceof AnnotationsAttribute) {
        System.err.format("Class %s has annotations bytecode attribute!%n", an6.name());
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

