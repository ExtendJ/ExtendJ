// Binary literals: too large value.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo = 0b10000000000000000000000000000000000000000000000000000000000000000L;
  }
}
