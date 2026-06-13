// Test that unnecessary checkcast instructions are not generated.
// https://bitbucket.org/extendj/extendj/issues/262/unnecessary-checkcast-converting-to-a
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.MethodDecl;

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
        + "public class An4 {"
        + "  public static void main(String[] args) {"
        + "    Map<String, String> score = new HashMap<String, String>();"
        + "    score.put(\"Pancake Batter\".toLowerCase(), \"very nice\");"
        + "  }"
        + "}";
    CompilationUnit cu = parseCompilationUnit(code);
    MethodDecl main = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);

    // Generate the bytecode and assert that there are no checkcast instructions.
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bos);
    main.bytecodes(main.hostType().constantPool()).printBytecodes(out);
    String bytecode = new String(bos.toByteArray());
    out.close();
    testTrue(bytecode.contains("invokespecial"));
    testTrue(bytecode.contains("dup"));
    testTrue(bytecode.contains("return"));
    testFalse(bytecode.contains("checkcast"));
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

