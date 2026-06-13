// This tests the behaviour of the isPrimitive attribute.
// Boxed primitive types are treated as primitive types.
// This should probably be changed eventually.
// https://bitbucket.org/extendj/extendj/issues/240/typedeclisprimitive-does-not-work-as
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.Program;
import org.extendj.ast.TypeDecl;

import static runtime.Test.testFalse;
import static runtime.Test.testTrue;

public class Test {
  public static void main(String[] args) {
    Program program = new Program();

    TypeDecl typeBool = program.typeBoolean();
    testTrue(typeBool.isPrimitive());
    testFalse(typeBool.isReferenceType());

    TypeDecl typeBoolRef = program.lookupType("java.lang", "Boolean");
    testTrue(typeBoolRef.isPrimitive());
    testTrue(typeBoolRef.isReferenceType());

    TypeDecl typeString = program.lookupType("java.lang", "String");
    testFalse(typeString.isPrimitive());
    testTrue(typeString.isReferenceType());
  }
}
