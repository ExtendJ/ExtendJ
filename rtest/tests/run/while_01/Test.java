// Test while-loop runtime execution.
public class Test {
  public static void main(String[] args) {
    int x = 0, i = 1;
    while (i <= 10) {
      x += i;
      i += 1;
    }
    System.out.println(x);
  }
}
