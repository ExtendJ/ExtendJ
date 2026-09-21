// Test assignment of the result of a nested invocation with an upper bounded wildcard result.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <P> Bisu<? extends P> inner(Bisu<P> b);
  abstract <T> T outer(Bisu<T> b);

  <Q extends Protoss> void probe(Bisu<Q> b) {
    // T is instantiated to a capture variable with the upper bound Protoss.
    Protoss p = outer(inner(b));

    // T is instantiated to a capture variable with the upper bound Q.
    Q target = outer(inner(b));
  }
}

interface Bisu<Z> { }
interface Protoss { }
