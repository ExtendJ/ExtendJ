// Test method binding for implicit close() method access in try-with-resources.
// https://bitbucket.org/extendj/extendj/issues/260/flaky-compilation-failures-with-java-7-twr
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.ParInterfaceDecl;
import org.extendj.ast.RawInterfaceDecl;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeVariable;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.TryWithResources;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Access;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static runtime.Test.testSame;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    // Repeat a few times to catch flaky error.
    for (int i = 0; i < 4; ++i) {
      String code =
        "import java.nio.channels.ReadableByteChannel;"
        + "import java.io.IOException;"
        + "class Foo {"
        + "  public void m(ReadableByteChannel in) throws IOException {"
        + "    try (ReadableByteChannel res = in) {"
        + "    }"
        + "  }"
        + "}";
      CompilationUnit cu = parseCompilationUnit(code);
      MethodDecl method = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
      TryWithResources twr = (TryWithResources) method.getBlock().getStmt(0);
      MethodDecl closeMethod = twr.getResource(0).closeAccess().decl();
      if (!closeMethod.hostType().fullName().equals("java.nio.channels.Channel")) {
        System.err.format("(%d) Unexpected host type for close(): %s%n",
            i, closeMethod.hostType().fullName());
        return;
      }
    }
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

