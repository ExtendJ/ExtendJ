// Test for-loop runtime execution.
// Constant-true condition.
public class Test {
  public static void main(String[] args) {
    int x;
    for (int i = 1; true; ++i) {
      x = i;
      break;
    }
    System.out.println(x);
  }
}
