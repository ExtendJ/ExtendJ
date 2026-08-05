// Test for incorrect inference of thrown exception type.
// See issue 308.
// .result: COMPILE_PASS
public class Test {
  void m() throws Exception {
    // Here X=Exception because f() throws Exception:
    doExceptionally(() -> f());
  }

  void f() throws Exception { }

  <X extends Throwable> void doExceptionally(ExceptionalFun<X> fun) throws X {
    fun.apply();
  }
}

interface ExceptionalFun<E extends Throwable> {
  void apply() throws E;
}
