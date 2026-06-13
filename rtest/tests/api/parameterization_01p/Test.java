// Test parameterized type construction and using the Parameterization.getArg(int) method.
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

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;

import static runtime.Test.testSame;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    String code = "interface Interface<U, V> { }";
    CompilationUnit cu = parseCompilationUnit(code);
    GenericInterfaceDecl decl = (GenericInterfaceDecl) cu.getTypeDecl(0);
    TypeDecl typeObject = cu.lookupType("java.lang", "Object");
    TypeDecl typeNumber = cu.lookupType("java.lang", "Number");
    TypeDecl typeString = cu.lookupType("java.lang", "String");
    ParInterfaceDecl par1 = (ParInterfaceDecl) decl.lookupParTypeDecl(
        Arrays.asList(typeNumber, typeString));
    RawInterfaceDecl par2 = (RawInterfaceDecl) decl.lookupParTypeDecl(
        Collections.<TypeDecl>emptyList());
    testSame(typeNumber, par1.getParameterization().getArg(0));
    testSame(typeString, par1.getParameterization().getArg(1));
    testSame(typeObject, par2.getParameterization().getArg(0));
    testSame(typeObject, par2.getParameterization().getArg(1));
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

