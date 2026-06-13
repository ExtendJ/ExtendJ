// Test stack overflow when searching for assignment context for a ParExpr
// inside a ConditionalExpr. The stack overflow is triggered by evaluating the
// mutually circular attributes assignmentContext and isPolyExpression.
// .result=COMPILE_PASS
public class Test {
  Test(String str) {
  }

  String getText() {
    return "";
  }

  void init(Object o) {
    new Test(o == null ? (getText()) : "");
  }
}
