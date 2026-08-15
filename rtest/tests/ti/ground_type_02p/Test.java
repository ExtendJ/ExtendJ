// Test grounding of lambda expressions inside parenthesis expression.
// .result: COMPILE_PASS
public abstract class Test {
  void test(boolean flag) {
    format((sector -> sector * 336));
  }

  abstract <B, C> C format(Disk<Integer, B> f);
}

interface Disk<I, O> {
  O format(I i);
}
