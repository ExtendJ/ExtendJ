public class Test {
  public static void main(String[] args) {
    int x, y, z;
    switch (f()) {
      case 0:
        x = 0;
        y = 0;
        break;
      case 1:
        x = 10;
        y = 1;
        break;
      default:
        x = 20;
        y = 2;
        z = 3;
        break;
    }
    System.out.println(x + y);
  }

  static int f() {
    return 9;
  }
}
