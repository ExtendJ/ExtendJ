// Test that the right modifiers are used for implicit enum methods.
// https://bitbucket.org/extendj/extendj/issues/283/enum-values-and-valueof-methods-should-not
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.EnumDecl;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Modifier;
import org.extendj.ast.SignatureAttribute;

import java.io.ByteArrayInputStream;

import static runtime.Test.testTrue;
import static runtime.Test.testFalse;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    String code = "enum Trait { SOFT, FLUFFY }";
    CompilationUnit cu = parseCompilationUnit(code);
    EnumDecl type = (EnumDecl) cu.getTypeDecl(0);

    checkMods(type.implicitValueOfMethod());
    checkMods(type.implicitValuesMethod());
  }

  static void checkMods(MethodDecl method) {
    // The only allowed modifiers are "public" and "static".
    for (Modifier modifier : method.getModifiers().getModifierList()) {
      String mod = modifier.getID();
      if (!mod.equals("public") && !mod.equals("static")) {
        System.err.format("Unexpected modifier %s for method %s.%n", mod, method.signature());
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

