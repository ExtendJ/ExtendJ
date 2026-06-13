// Test that methods from java.lang.Object can be called on an unbounded wildcard type.
// .result=COMPILE_PASS
public class Test {
  int test(C<?> c) {
    return c.value.hashCode();
  }
}

class C<T> {
  T value;
}
