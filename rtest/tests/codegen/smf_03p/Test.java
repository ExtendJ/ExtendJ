// https://bitbucket.org/extendj/extendj/issues/245/stackmap-frame-error-in-constructor
public class Test {
  int sum;

  Test() {
    this(0x523, 0x941, 0x22);
    if (sum == 3718) {
      System.out.println("pass");
    }
  }

  Test(int x) {
    sum = x;
  }

  Test(int x, int y) {
    this(x);
    sum += y;
  }

  Test(int x, int y, int z) {
    this(x, y);
    sum += z;
  }

  public static void main(String[] args) {
    new Test();
  }
}
