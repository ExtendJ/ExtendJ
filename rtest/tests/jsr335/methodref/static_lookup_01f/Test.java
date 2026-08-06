// A static method found by the instance-style search (JLS8 §15.13.1) does not
// make the method reference congruent, so the invocation cannot be resolved.
//
// This is a regression test for a crash in code generation.  Previously,
// failed overload resolution caused the ExtendJ to throw NullPointerException
// during code generation.
// .result: COMPILE_FAIL
public class Test {
  interface SHcqNVjGJN4 { int apply(B b); }

  static String exec(SHcqNVjGJN4 f) {
    return "SHcqNVjGJN4";
  }

  public static void main(String[] args) {
    System.out.println(exec(B::f));
  }
}

class B {
  static int f() {
    return 42;
  }
}
