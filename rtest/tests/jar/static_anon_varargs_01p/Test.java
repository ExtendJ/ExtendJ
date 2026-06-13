// Test that bytecode parsing works even when there exists an inner class used
// for a static anonymous class instantiation with a variable arity parameter
// constructor.
// This highly specific test case requires loading bytecode, so the companion
// regtest.jar is included. The source for this Jar is Enclosing.java.original.
// The bytecode was compiled with Javac 1.8.0, but the output was generated in
// 1.6 mode.
// https://bitbucket.org/extendj/extendj/issues/175/amazon-s3-jar-null-pointer-exception
// .result=COMPILE_PASS
// .sources=Test.java
// .classpath=@TEST_DIR@\/regtest.jar
import pkg.Enclosing;
class Test extends Enclosing {
}
