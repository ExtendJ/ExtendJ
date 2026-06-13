// Type argument inference through actual constructor arguments.
// .result=COMPILE_PASS
class Test {

  class T6_3_A<T> {
    public T a;
    T6_3_A(String s, T t) {
      a = t;
    }
  }

  {
    new T6_3_A<>("bar", new Error()).a.printStackTrace(System.out);
  }
}
