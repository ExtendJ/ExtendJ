// Test pretty-printing for statement without init statement.
// .result=COMPILE_OUT
// .options=XprettyPrint
class Test {
  void test() {
    for ( ; ; ) {
      break;
    }
  }
}
