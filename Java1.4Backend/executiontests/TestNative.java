public class TestNative {
  native void v();
  native int i(int a1, char a2, String a3);
  native static int si();

  public static void main(String[] args) {
    TestNative t = new TestNative();
    t.v();
    System.out.println(t.i(1, 'A', "test"));
    System.out.println(si());
  }
}
