// Test that a bridge method is generated for a final method.
// https://bitbucket.org/extendj/extendj/issues/265/missing-bridge-method-generation-for-final
public class Test {
  public static void main(String[] args) {
    System.out.println(foo(new X()));
  }

  static Object foo(S s) {
    return s.x();
  }
}

class S {
  Object x() {
    return null;
  }
}

class X extends S {
  final String x() {
    return "x marks the spot";
  }
}
