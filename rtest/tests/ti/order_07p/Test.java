// Test constraint ordering in inference.
// .result: COMPILE_PASS
public abstract class Test {
  {
    nestedctorref(s -> s.length(), Box::new);
  }

  static class Box<E> {
    final E e;

    Box(E val) {
      this.e = val;
    }
  }

  abstract <B, C> C nestedctorref(Mix<String, B> f, Mix<B, C> g);
}

interface Mix<I, O> {
  O apply(I i);
}
