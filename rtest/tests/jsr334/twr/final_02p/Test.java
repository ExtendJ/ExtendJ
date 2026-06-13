// The resource is final inside the TWR block.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (java.io.PrintStream r = System.out) {
      r = System.err;
    }
  }
}
