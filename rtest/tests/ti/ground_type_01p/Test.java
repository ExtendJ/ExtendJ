// Test grounding of lambda expressions inside conditional expression.
// .result: COMPILE_PASS
public abstract class Test {
  void test(boolean copy) {
    format(copy ? n -> n * 547 : n2 -> n2 - 410);
  }

  abstract <B, C> C format(Disk<Integer, B> f);
}

interface Disk<I, O> {
  O format(I i);
}
