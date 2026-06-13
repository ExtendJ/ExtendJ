// Binary literals: there are no binary floating point literals.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    double bar = 0b.1;
  }
}
