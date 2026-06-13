// Test that flushTreeCache flushes all loaded types.
// https://bitbucket.org/extendj/extendj/pull-requests/5
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.TypeDecl;
import org.extendj.ast.Program;

import java.io.ByteArrayInputStream;

import static runtime.Test.testNotSame;

public class Test {
  public static void main(String[] args) {
    Program program = new Program();
    program.initBytecodeReader(Program.defaultBytecodeReader());
    TypeDecl before = program.lookupType("java.lang", "Object");
    program.flushTreeCache();
    TypeDecl after = program.lookupType("java.lang", "Object");
    testNotSame(before, after);
  }
}

