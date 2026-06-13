// Test that the bytecode invocation of a generic constructor is generated with
// the correct signature (using the type parameter type bounds, not the type
// arguments bounds).
public class Test {
  static class C {
    <U extends Number> C(U u) {
    }
  }

  public static void main(String[] args) {
    new C(10);
  }
}
