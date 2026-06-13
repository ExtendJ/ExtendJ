// A variable arity method is more specific when passed an array type.
// .result=EXEC_PASS
public class Test {
  static boolean pass(Object p) {
    return true;
  }
  static boolean pass(Object... p) {
    return false;
  }
  public static void main(String[] args) {
    if (!pass("abc")) {
      throw new Error("Wrong method called.");
    }
  }
}
