// Bridge methods are generated when, due to type erasure, a method in a
// subtype that should override a method in a supertype or interface no longer
// has the same signature as the method it overrides.
// This test checks that a polymorphic call uses a generated bridge method.
// .result=EXEC_PASS
public class Test {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    I<Integer> sr = new C();
    test(sr.taxi(), 1729);
  }

  static void test(int actual, int expected) {
    if (actual != expected) {
      throw new Error("expected " + expected + " but got " + actual);
    }
  }
}

interface I<T> {
  T taxi();
}

class C1 {
  public Integer taxi() {
    return 1729;
  }
}

class C extends C1 implements I<Integer> {
}
