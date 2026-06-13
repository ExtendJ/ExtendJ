// Test that a simple generic type instantiation that needs a constructor
// accessor method works at runtime.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    C<Integer> c = new C<Integer>();
  }

  private static class C<T> {
  }
}
