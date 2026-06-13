// Unresolved type arguments.
// .result=COMPILE_PASS
class Test {

  class T9_1_A<T, U, V> {
    public T a;
    public U b;
    public V c;
    T9_1_A(T t, V v) {
      a = t;
      c = v;
    }
  }

  {
    new T9_1_A<>(true, false).a.compareTo(Boolean.valueOf(true));
  }
}
