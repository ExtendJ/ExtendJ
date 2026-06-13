// Test wildcard type substitution in code generation.
// This test checks that wildcard type substitution works in code
// generation. The type of i.val in intValue(C<?>) is ? extends Number.
// The test checks that bytecode is correctly generated for the
// expression i.val.intValue();
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (intValue(new C<Integer>(1001)) != 1001) {
      throw new Error();
    }
  }

  static class C<T extends Number> {
    public final T val;

    public C(T t) {
      val = t;
    }
  }

  static int intValue(C<?> i) {
    return i.val.intValue();
  }
}
