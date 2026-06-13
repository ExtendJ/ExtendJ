// This test compiles with Java 8+, but fails in earlier versions.
// https://bitbucket.org/extendj/extendj/issues/309/java-8-exception-handling-with-inferred
// .result: COMPILE_PASS
public class Test {
  void m() {
    f();
  }

  <E extends Exception> void f() throws E { }
}
