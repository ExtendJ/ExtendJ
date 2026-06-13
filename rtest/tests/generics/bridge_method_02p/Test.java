// Bridge methods are generated when, due to type erasure, a method in a
// subtype that should override a method in a supertype or interface no longer
// has the same signature as the method it overrides.
// This test checks that a polymorphic call uses a generated bridge method.
// .result=EXEC_PASS
public class Test {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Generic raw = new Subtype();
    test(raw.bridged((Object) 12), 57);
    test(raw.bridged(Integer.valueOf(13)), 37);
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

  int bridged(Number p) {
    return 37;
  }
}

class Subtype extends Generic<Integer> {
  int bridged(Integer p) {
    return p + super.bridged(p);
  }
}
