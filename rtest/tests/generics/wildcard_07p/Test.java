// Test wildcard type substitution and casting conversion.
// This test checks that there is a casting conversion between
// the type ? super Number and null. In general this tests that
// casting conversion works for wildcard types.
// .result=COMPILE_PASS
public class Test {
  interface I<T> {
    T get();
  }

  boolean test(I<? super Number> i) {
    return i.get() != null;
  }
}
