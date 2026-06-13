// Tests bytecode generation problem concerning finally clause.
//
// https://bitbucket.org/jastadd/jastaddj/issue/12/finally-clause-bytecode-generation-error
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    try {
    } finally {
      int[] a = new int[3];
    }
  }
}
