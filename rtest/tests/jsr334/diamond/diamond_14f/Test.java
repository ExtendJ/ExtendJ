// Type argument inference through actual constructor arguments.
// .result=COMPILE_FAIL
class Test {

  class T6_2_A<T> {
    public T a;
    <U extends Throwable> T6_2_A(T t, U u) throws U {
      a = t;
    }
  }

  {
    // In this invocation of T6_2_A, U is Throwable which is a checked exception type
    // so we need to catch the exception which the constructor declares thrown.
    new T6_2_A<>("bar", new Throwable()).a.getBytes();
  }
}
