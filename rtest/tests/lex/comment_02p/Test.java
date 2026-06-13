// Test a lexical error causing comments to span more than they should.  In
// this case the first documentation comment extends to the second one,
// removing the main method.
// See https://bitbucket.org/extendj/extendj/issues/144/comment-lexing-error
// .result=EXEC_PASS
public class Test {
  /***/
  public static void main(String[] args) {
    new Test();
  }
  /***/

  public Test() {
  }
}
