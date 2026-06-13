// Test for type inference type variables inside type bounds.
// https://bitbucket.org/extendj/extendj/issues/174/class-typed-method-parameter-no-method
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
