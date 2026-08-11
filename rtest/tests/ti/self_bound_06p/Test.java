// Same as self_bound_05p, but the second upper bound is the one the invocation needs.
// .result: COMPILE_PASS
public class Test {
  interface Recursive<E> { }
  static <T extends Recursive<T> & Cloneable> T pick() { return null; }
  static void take(Cloneable c) { }
  {
    take(pick());
  }
}
