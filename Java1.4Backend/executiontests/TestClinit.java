public class TestClinit {
  static int i = 1;
  static int j;
  static {
    j = 2;
  }
  public static void main(String[] args) {
    System.out.println(i);
    System.out.println(j);
  }
}
