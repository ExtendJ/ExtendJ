// https://bitbucket.org/extendj/extendj/issues/259/method-applicability-error-for-generic
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
