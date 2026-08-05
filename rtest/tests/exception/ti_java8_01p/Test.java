// This test compiles with Java 8+, but fails in earlier versions.
// See issue 309.
// .result: COMPILE_PASS
public class Test {
  void m() {
    f();
  }

  <E extends Exception> void f() throws E { }
}
