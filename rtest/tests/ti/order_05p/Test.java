// Test constraint ordering in inference.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // Three constraints in a dependency cycle: each lambda's parameter type is
    // determined by another lambda's result. The cycle is broken by reducing the
    // leftmost constraint first (JLS SE8 18.5.2), giving every variable its
    // declared bound.
    cycle(x -> x, y -> y, z -> z);
  }

  abstract <A, B, C> void cycle(Cycle<A, B> f, Cycle<B, C> g, Cycle<C, A> h);
}

interface Cycle<I, O> {
  O bi(I i);
}
