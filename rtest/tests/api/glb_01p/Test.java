// Test class and array bounds of type variables and intersections.
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FieldDecl;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericClassDecl;
import org.extendj.ast.GLBTypeFactory;
import org.extendj.ast.ParClassDecl;
import org.extendj.ast.Program;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeDecl;

import static runtime.Test.testNull;
import static runtime.Test.testSame;

public class Test {
  public static void main(String[] args) throws Exception {
    Program program = new Program();
    program.initBytecodeReader(Program.defaultBytecodeReader());
    program.initJavaParser(Program.defaultJavaParser());
    String code = "class Saturn<E extends Ring & Moon> { Saturn<?> field; }"
        + "interface Ring {} interface Moon {}"
        + "class Jupiter<E extends F, F extends E> {}";
    CompilationUnit unit = Program.defaultJavaParser().parse(
        new ByteArrayInputStream(code.getBytes("UTF-8")), "Test.java");
    program.addCompilationUnit(unit);
    unit.setClassSource(new FileClassSource(new SourceFolderPath("."), "Test.java"));
    unit.setFromSource(true);

    GenericClassDecl saturn = (GenericClassDecl) unit.getTypeDecl(0);
    TypeDecl ring = unit.getTypeDecl(1);
    TypeDecl moon = unit.getTypeDecl(2);
    TypeDecl intersection = ring.lookupGLBType(Arrays.asList(ring, moon));
    FieldDecl field = (FieldDecl) saturn.getBodyDecl(0);
    TypeDecl capture = ((ParClassDecl) field.getTypeAccess().capturedType())
        .getParameterization().getArg(0);

    TypeDecl number = unit.lookupType("java.lang", "Number");
    TypeDecl string = unit.lookupType("java.lang", "String");
    TypeDecl object = unit.lookupType("java.lang", "Object");
    TypeDecl nested = ring.lookupGLBType(Arrays.asList(intersection, number));
    testSame(number, GLBTypeFactory.mostSpecificSuperClass(Arrays.asList(nested)));
    testNull(GLBTypeFactory.mostSpecificSuperClass(Arrays.asList(nested, string)));
    testNull(GLBTypeFactory.mostSpecificSuperClass(Arrays.asList(string.arrayType(), number)));
    testSame(string.arrayType(), GLBTypeFactory.mostSpecificSuperClass(
        Arrays.asList(string.arrayType(), object)));
    testSame(number, GLBTypeFactory.mostSpecificSuperClass(Arrays.asList(capture, number)));
    GenericClassDecl jupiter = (GenericClassDecl) unit.getTypeDecl(3);
    testSame(object, GLBTypeFactory.mostSpecificSuperClass(
        Arrays.<TypeDecl>asList(jupiter.getTypeParameter(0))));
  }
}
