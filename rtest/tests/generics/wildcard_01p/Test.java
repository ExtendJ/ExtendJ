// Test wildcard type substitution and method lookup.
// This test checks that the call i.get().intValue() finds
// the correct intValue() method in java.lang.Number.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (intValue(new C<Integer>(1001)) != 1001) {
      throw new Error();
    }
  }

  static class C<T extends Number> {
    private final T val;

    public C(T t) {
      val = t;
    }

    public T get() {
      return val;
    }
  }

  static int intValue(C<?> i) {
    return i.get().intValue();
  }
}

