// Test wildcard type substitution in code generation.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    Integer i = 1001;
    if (unwrap(new C<Integer>(i)) != i) {
      throw new Error();
    }
  }

  static class C<T extends Number> {
    public final T val;

    public C(T t) {
      val = t;
    }
  }

  static Object unwrap(C<? super Integer> i) {
    return i.val;
  }
}
