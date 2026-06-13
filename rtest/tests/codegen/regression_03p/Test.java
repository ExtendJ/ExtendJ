// Test regression in code generation causing enclosing variables to be discarded.
// .result=EXEC_PASS

public class Test {
  static int f = 2020;

  static {
    final int x = -1010;
    f = new Test() { 
      int f = x;
    }.f;
  }

  public static void main(String[] args) {
    if (f != -1010) {
      throw new Error("expected -1010");
    }
  }
}
