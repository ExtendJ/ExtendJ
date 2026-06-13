// The resource may not have the same name as a resource declared earlier in
// the resource specification.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (java.io.PrintStream r = System.out;
        java.io.PrintStream r = System.err) {
    }
  }
}
