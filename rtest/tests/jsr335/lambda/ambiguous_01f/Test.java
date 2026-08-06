// Test an implicitly typed lambda inference in ambiguous method invocation.
// .result: COMPILE_FAIL
public class Test {
  interface Cronion   { int cronion(); }
  interface ShieldBug { int shieldbug(); }

  static void beetle(Cronion fc) { }
  static void beetle(ShieldBug fs) { }

  {
    exec(() -> 1); // More than one most specific applicable method.
  }
}
