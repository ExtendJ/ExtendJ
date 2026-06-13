// Test that an unbounded wildcard type can be assigned to a variable of
// type java.lang.Object.
// .result=COMPILE_PASS
public class Test {
  void test(C<?> c) {
    Object value = c.value;
  }
}

class C<T> {
  T value;
}
