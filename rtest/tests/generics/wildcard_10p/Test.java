// See issue 259.
// .result: COMPILE_PASS
public class Test {
  void add(Container<? super X> xs) {
    xs.add(new Y());
  }

  static class X { }
  static class Y extends X { }
}

interface Container<T> {
  void add(T t);
}
