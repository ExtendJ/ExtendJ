// Bridge methods are generated when, due to type erasure, a method in a
// subtype that should override a method in a supertype or interface no longer
// has the same signature as the method it overrides.
// This test checks that a polymorphic call uses a generated bridge method.
// .result=EXEC_PASS
public class Test {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Generic raw = new Subtype();
    test(raw.bridged("gg"), 90);
  }

  static void test(int actual, int expected) {
    if (actual != expected) {
      throw new Error("expected " + expected + " but got " + actual);
    }
  }
}

class Generic<T> {
  int bridged(T p) {
    return 45;
  }
}

class Subtype extends Generic<String> {
  int bridged(String p) {
    return 45 + super.bridged(p);
  }
}
