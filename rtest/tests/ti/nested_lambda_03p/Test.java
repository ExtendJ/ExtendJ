// Test captured type inside lambda body compared against the ground lambda type.
// .result: COMPILE_PASS
public class Test {
  Canvas<Shape<?>> canvas;

  void art() {
    canvas.paint(form -> form);
  }
}

interface Shape<T> { }
interface Canvas<F> {
  <R> Canvas<R> paint(Artist<? super F, ? extends R> a);
}
interface Artist<U, P> {
  P create(U u);
}
