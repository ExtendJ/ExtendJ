// Test for incorrect inference of thrown exception type.
// https://bitbucket.org/extendj/extendj/issues/308/error-in-uncaught-exception-checking-for
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
