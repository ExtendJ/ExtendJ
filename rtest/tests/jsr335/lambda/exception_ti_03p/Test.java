// Regression test for exception handling issue.
// Exception checking interacts in intricate ways with type inference.
// https://bitbucket.org/extendj/extendj/issues/308/error-in-uncaught-exception-checking-for
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
