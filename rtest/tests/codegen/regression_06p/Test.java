// Test regression in code generation for an anonymous class extending a
// locally declared class.
// .result=EXEC_PASS
import java.io.PrintStream;

public class Test {
  public static void main(String[] args) {
    test(System.out);
  }

  static void test(final PrintStream out) {
    class Local {
      void print(String message) {
        out.println(message);
      }
    }
    Local anon = new Local() {
    };
    anon.print("expected output");
  }
}
