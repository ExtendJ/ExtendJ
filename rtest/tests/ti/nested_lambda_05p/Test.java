// Test captured type in the result of a nested lambda compared against the ground lambda type.
// .result: COMPILE_PASS
public abstract class Test {
  abstract Shape<?> shape();
  abstract <T> void frame(Artist<Boolean, Tile<Shape<T>>> a);

  {
    frame(q -> () -> shape());
  }
}

interface Shape<T> { }
interface Artist<U, P> {
  P create(U u);
}
interface Tile<E> {
  E tile();
}
