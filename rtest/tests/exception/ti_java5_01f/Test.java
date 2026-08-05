// This test does not compile with Java 5-7, but compiles with Java 8.
// See issue 309.
// .result: COMPILE_FAIL
public class Test {
  void m() {
    f();
  }

  <E extends Exception> void f() throws E { }
}
