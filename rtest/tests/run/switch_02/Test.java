// Test fallthrough.
public class Test {
  public static void main(String[] args) {
    int x = 0, y = 0, z;
    switch (f()) {
      case 0:
        x += 5;
        y += 3;
        break;
      case 1:
        x += 10;
        y += 1;
      default:
        x += 20;
        y += 2;
        z = 3;
        break;
    }
    System.out.println(x + y);
  }

  static int f() {
    return 1;
  }
}
