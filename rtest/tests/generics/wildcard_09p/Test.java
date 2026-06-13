// Test that methods from a wildcard bound type can be called on the
// wildcard type.
// .result=COMPILE_PASS
public class Test {
  int test(C<? extends Number> c) {
    return c.value.intValue();
  }
}

class C<T> {
  T value;
}
