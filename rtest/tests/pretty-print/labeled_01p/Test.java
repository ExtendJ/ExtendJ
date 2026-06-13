// Labeled statements should be prett-print with both the label and the statement.
// https://bitbucket.org/extendj/extendj/issues/177/label-stmt-pretty-print
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
