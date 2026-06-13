// Test an error in stack map frame computation caused by
// wide types not being handled correctly.
public class Test {
  public static void main(String[] args) {
    {
      int i = 32394;
      int[] ia = { 1, 2 };
      int[] ia2 = { 1, 2 };
      if (i != 32394) return;
      System.out.print("1");
    }
    {
      long[] la = { 1l, 2l };
      long l = 6864468644l;
      if (l != 6864468644l) return;
      System.out.print("2");
    }
    {
      float f = .9f;
      float[] fa = { Float.NaN, Float.NaN };
      if (f != .9f) return;
      System.out.print("3");
    }
  }
}
