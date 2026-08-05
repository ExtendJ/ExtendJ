// Labeled statements should be prett-print with both the label and the statement.
// See issue 177.
// .result=COMPILE_OUT
// .options=XprettyPrint
class Test {
  void m() {
MyLabel:
    {
      int x;
    }
  }
}
