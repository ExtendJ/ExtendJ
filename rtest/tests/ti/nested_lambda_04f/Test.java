// Test captured type inside lambda body compared against the ground lambda type.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract Shape<?> can();
  abstract Shape<?> vas();
  abstract <T> void swap(Artist<Boolean, Shape<T>> a);

  {
    swap(q -> { if (q) return can(); else return vas(); });
  }
}

interface Shape<T> { }
interface Artist<U, P> {
  P create(U u);
}
