// Unresolved type arguments.
// .result=COMPILE_FAIL
class Test {

  class T9_2_A<T, U, V> {
    public T a;
    public U b;
    public V c;
    T9_2_A(T t, V v) {
      a = t;
      c = v;
    }
  }

  {
    new T9_2_A<>(true, false).b.compareTo(new Boolean(true));
  }
}
