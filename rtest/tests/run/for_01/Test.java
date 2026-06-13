// Test for-loop runtime execution.
public class Test {
  public static void main(String[] args) {
    int x = 0;
    for (int i = 1; i <= 10; ++i) {
      x += i;
    }
    System.out.println(x);
  }
}
