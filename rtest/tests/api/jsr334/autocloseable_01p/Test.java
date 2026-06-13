// Test that the type AutoCloseable has a close() method that throws
// only the exception type java.lang.Exception.
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
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Access;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static runtime.Test.testSame;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    Program program = new Program();
    program.initBytecodeReader(Program.defaultBytecodeReader());
    program.initJavaParser(Program.defaultJavaParser());
    TypeDecl typeAutoCloseable = program.lookupType("java.lang", "AutoCloseable");
    boolean foundClose = false;
    for (MethodDecl method : typeAutoCloseable.memberMethods("close")) {
      foundClose = true;
      if (method.getNumParameter() == 0) {
        if (method.getNumException() == 0) {
          System.err.println("AutoCloseable.close() has no thrown exception types.");
        }
        for (Access excp : method.getExceptionList()) {
          TypeDecl excpType = excp.type();
          if (!excpType.isType("java.lang", "Exception")) {
            System.err.format("Unexpected exception type for AutoCloseable.close(): %s%n",
                excpType.fullName());
          }
        }
      }
    }
    if (!foundClose) {
      System.err.println("Could not find AutoCloseable.close().");
    }
  }
}

