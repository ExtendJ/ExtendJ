public class Test {
  public static void main(String[] args) {
    try {
      int i;
      int[] a = f();
      i = a[0];
      i += 2 * a[1];
      i += 4 * a[2];
      i += 8 * a[3];
      i += 16 * a[4];
      i += 32 * a[5];
      i += 64 * a[6];
      System.out.println(i);
      System.out.println(77);
    } catch (Error t) {
      throw t;
    }
  }

  static int[] f() {
    return new int[] { 1, 0, 1, 1, 0, 0, 1 };
  }
}
