// Two inference variables joined by an equality bound reach the second
// instantiation attempt of resolution which leads to inference failure.
// .result: COMPILE_FAIL
public abstract class Test {
  interface DaVinci<S> {}
  static class Leonardo<U> {}

  abstract <Vp extends DaVinci<Vp>> Leonardo<Vp> idea();

  abstract <Vq> Vq invent(Leonardo<Vq> c);

  void test() {
    Object o = invent(idea());
  }
}
