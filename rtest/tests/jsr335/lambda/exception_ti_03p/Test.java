// Regression test for exception handling issue.
// Exception checking interacts in intricate ways with type inference.
// See issue 308.
// .result: COMPILE_PASS
public class Test {
  void m() throws Exception {
    doExceptionally(() -> f());
  }

  void f() throws Exception { }

  <E extends Throwable> void doExceptionally(ExceptionalListener<E> fun) throws E {
    fun.apply();
  }
}

interface ExceptionalListener<E extends Throwable> {
  void apply() throws E;
}
