// Test for type inference type variables inside type bounds.
// See issue 174.
// .result=COMPILE_PASS

public class Test {
  void m() {
    newE(D.class);
  }

  <KEY, T extends E<KEY>> T newE(Class<T> ct) {
    return null;
  }
}

interface E<K> {}
interface D extends E<Long> {}
