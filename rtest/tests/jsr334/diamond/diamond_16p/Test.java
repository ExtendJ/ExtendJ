// Type argument inference through assignment conversion.
// .result=COMPILE_PASS
class Test {

  class T7_2_A<T> {
    public T a;
    <U, V> T7_2_A() { }
    void set(T t) {
      a = t;
    }
  }

  {
    T7_2_A<Integer> obj = new T7_2_A<>();
    obj.set(4);
    obj.a.compareTo(Integer.valueOf(4));
  }
}
