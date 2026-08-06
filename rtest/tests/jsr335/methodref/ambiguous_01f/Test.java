// Test an ambiguous method call using an implicitly typed method reference.
// .result: COMPILE_FAIL
public class Test {
  interface Cronion   { int cronion(Beetle beetle); }
  interface ShieldBug { int shieldbug(); }

  static int exec(Cronion fc)   { return 0; }
  static int exec(ShieldBug fs) { return 0; }

  {
    exec(Beetle::f); // Ambiguous call.
  }
}

class Beetle {
  static int f(Beetle beetle) {
    return 1;
  }

  static int f() {
    return 2;
  }
}
