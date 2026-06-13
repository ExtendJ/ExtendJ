// Binary literals: too large.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo;
    foo = 0b1000000000000000000000000000000000000000000000000000000000000000;
  }
}
