public class Test {
  public static void main(String[] args) {
    int i = 0;
    int sum = 0;
    while (i < 100) {
      sum += i * i;
      ++i;
    }
    System.out.println(sum);
  }
}
