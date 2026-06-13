public class Test {
  static boolean compare(double x) {
    double d = 124.88404;
    return (d != x);
  }

  public static void main(String[] args) {
    System.out.println(compare(124.88404));
    System.out.println(compare(1001.88404));
  }
}
