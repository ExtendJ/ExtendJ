public class TestClinitMore {
  static int i = 1;
  static int j;
  static {
    j = i+1;
  }
  public static void printVars() {
    System.out.println(i);
    System.out.println(j);
  }
  public static void main(String[] args) {
    new TestClinitMore();
    printVars();
    i = i + 1;
    if(i<5)
     main(args);
  }
}
