// Tests conditional expression type analysis.
// .result=COMPILE_PASS
class Test {
  String s = "" + (true ? 1 : null);
}
