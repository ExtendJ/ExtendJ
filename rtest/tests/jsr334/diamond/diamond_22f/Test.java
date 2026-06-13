// Type argument inference affects exception checking.
// .result=COMPILE_FAIL
class Test {

  class C<T> {
    public T a;
    <U extends Throwable> C(T t, U u) throws U {
      a = t;
    }
  }

  {
    // In this invocation of C, U is Exception which is a checked exception type
    // so since it is not caught this causes a compile error.
    new C<>("bar", new Exception()).a.getBytes();
  }
}
