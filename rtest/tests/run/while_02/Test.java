// Test while-loop runtime execution.
// Constant-true condition.
public class Test {
  public static void main(String[] args) {
    int i = 0;
    int x;
    while (true) {
      i += 1;
      x = i;
      break; // Only one iteration.
    }
    System.out.println(x);
  }
}
