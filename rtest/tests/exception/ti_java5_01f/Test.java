// This test does not compile with Java 5-7, but compiles with Java 8.
// https://bitbucket.org/extendj/extendj/issues/309/java-8-exception-handling-with-inferred
// .result: COMPILE_FAIL
public class Test {
  void m() {
    f();
  }

  <E extends Exception> void f() throws E { }
}
