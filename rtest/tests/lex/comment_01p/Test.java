// Test a lexical error causing comments to span more than they should.
// In this case the first empty comment extends to the second one, removing the main method.
// See issue 144.
// .result=EXEC_PASS
public class Test {
  /**/
  public static void main(String[] args) {
    new Test();
  }

  /**/
  public Test() {
  }
}
