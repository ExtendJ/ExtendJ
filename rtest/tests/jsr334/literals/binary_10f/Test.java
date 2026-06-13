// Binary literals: too large value.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo = 0b100000000000000000000000000000000;
  }
}
