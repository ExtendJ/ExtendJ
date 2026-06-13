// Type argument inference through assignment conversion.
// .result=COMPILE_PASS
class Test {

  class T7_3_A<T, U, V> {
    public T a;
    public U b;
    public V c;
    T7_3_A() { }
    void set(T t, U u, V v) {
      a = t;
      b = u;
      c = v;
    }
  }

  {
    T7_3_A<Integer, Boolean, String> obj = new T7_3_A<>();
    obj.set(4, true, "hej");
    obj.a.compareTo(Integer.valueOf(4));
    obj.b.compareTo(Boolean.valueOf(true));
    obj.c.compareTo("hej");
  }
}
