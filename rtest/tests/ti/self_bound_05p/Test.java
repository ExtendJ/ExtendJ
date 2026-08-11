// Test a self-bounded type parameter with more than one upper bound.
// .result: COMPILE_PASS
public class Test {
  interface Recursive<E> { }
  interface Tag { }
  static <T extends Recursive<T> & Tag> T pick() { return null; }
  {
    Object o = pick();
  }
}
