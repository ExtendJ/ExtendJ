// Test that capture conversion introduces fresh type variables each time it is applied.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <T> void h(Box<T> a, Box<T> b);

  void m(Box<?> p, Box<?> q) {
    h(p, q);
  }
}

interface Box<T> { }
