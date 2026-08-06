// Congruence of a method reference used as a lambda body. B::f is congruent
// with I via the static-form declaration f(B). The static method f() found by
// the instance-style search (JLS8 15.13.1) has the wrong staticness for that
// form and must not make the reference ambiguous.
// .result: EXEC_PASS
public class Test {
  interface I { int apply(B b); }

  interface S { I get(); }

  public static void main(String[] args) {
    S s = () -> B::f;
    int r = s.get().apply(new B());
    if (r != 1) {
      throw new Error("expected 1, got " + r);
    }
  }
}

class B {
  static int f(B b) {
    return 1;
  }

  static int f() {
    return 2;
  }
}
