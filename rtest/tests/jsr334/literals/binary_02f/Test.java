// Missing value digits in binary literal.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo = 0b;
  }
}
