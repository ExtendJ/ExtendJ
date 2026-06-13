// Binary literals: illegal digit.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo;
    foo = 0b1F;
  }
}
