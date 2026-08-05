// Java 8 added static interface methods.
// This exposed a bytecode generation issue - wrong invoke instruction was emitted.
// See issue 220.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    I.foo();
  }
}

interface I {
  static void foo() {}
}
