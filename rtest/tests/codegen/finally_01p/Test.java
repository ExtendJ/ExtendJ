// Test for bytecode generation problem that caused the finally clause to
// clobber local variables.
//
// https://bitbucket.org/jastadd/jastaddj/issue/4/throwstmt-bytecode-error
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    System.out.println(m());
  }

  public static boolean m() {
    try {
      String x = new String();
      String s = new String();
      try {
        return true;
      } catch (Throwable t) {
        s.toString();
        return false;
      }
    } finally {
    }
  }
}
