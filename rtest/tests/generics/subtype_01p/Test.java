// Test subtyping relation for wildcard parameterized types.
// .result=COMPILE_PASS
public class Test {
  Klass<?> test(Klass<?> klass) {
    return klass.superklass();
  }

  interface Klass<T> {
    Klass<? super T> superklass();
  }
}
