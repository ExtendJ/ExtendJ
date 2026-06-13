// A variable arity method is more specific when passed an array type.
// .result=EXEC_PASS
public class Test {
  static boolean pass(Object p) {
    return false;
  }
  static boolean pass(Object... p) {
    return true;
  }
  public static void main(String[] args) {
      if (!pass(new String[0])) {
      throw new Error("Wrong method called.");
    }
  }
}
