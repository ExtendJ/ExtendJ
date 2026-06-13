// Test wildcard type substitution for a field access.
// This test checks that the field val in C<?> can be accessed
// and that it can be added with a String.
// .result=COMPILE_PASS
public class Test {
  String test(C<?>[] a) {
    return " " + a[0].val;
  }
}

class C<T> {
  T val;
}
