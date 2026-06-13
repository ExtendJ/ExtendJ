// Illegal uses of underscore in numeric literals.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo;
    double bar;
    foo = _0b;
    foo = 1_;
    foo = 0_L;
    bar = 0x._FP1D;
    bar = 0x.F_P1f;
    bar = 0x_.FP1;
    bar = 1._2;
    bar = 0.1_D;
  }
}
