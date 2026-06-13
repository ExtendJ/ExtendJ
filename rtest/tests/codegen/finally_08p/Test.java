// Test a code generation error.
// Crash caused by unplaced label in simple try-statement.
// https://bitbucket.org/extendj/extendj/issues/311/code-generation-crashes-from-unplaced
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    System.out.println(m());
  }

  public static boolean m() {
    try {
      return true;
    } finally {
      return false;
    }
  }
}
