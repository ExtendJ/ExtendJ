// Tests a regression in codegen regarding enclosing variable handling.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    test("foo", 333);
  }

  static void test(final String name, final int size) {
    new Object() {
      // Needs enclosing variables for the name and size parameters.
      {
        if (!name.equals("foo") || size != 333) {
          throw new Error("expected \"foo\", 333");
        }
      }
    };
  }
}
