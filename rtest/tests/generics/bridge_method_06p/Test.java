// Bridge methods are generated when, due to type erasure, a method in a
// subtype that should override a method in a supertype or interface no longer
// has the same signature as the method it overrides.
// This test checks that bridge methods are properly generated in anonymous class
// declarations.
// .result=EXEC_PASS
public class Test {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    TaxiCab<Integer> sr = new TaxiCab<Integer>() {
      public Integer number() {
        return 1729;
      }
    };
    test(sr.number(), 1729);
  }

  static void test(int actual, int expected) {
    if (actual != expected) {
      throw new Error("expected " + expected + " but got " + actual);
    }
  }
}

interface TaxiCab<T> {
  T number();
}
