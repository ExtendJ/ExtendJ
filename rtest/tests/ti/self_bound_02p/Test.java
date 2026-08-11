// Same as self_bound_01p, but the recursive bound is a class rather than an interface.
// .result: COMPILE_PASS
public class Test {
  abstract static class Node<N extends Node<N>> { }

  static <N extends Node<N>> N mjs() { return null; }

  Object sEf_q3y2mjs = mjs();
}
