// Java 8 added static interface methods.
// This exposed a bytecode generation issue - wrong invoke instruction was emitted.
// https://bitbucket.org/extendj/extendj/issues/220/intstream-and-doublestream-does-not-work
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    I.foo();
  }
}

interface I {
  static void foo() {}
}
