public class TestRemovePop {
  public int i = 0;
  public long l = 0;
  public int[] a = {0,1,2,3};
  public static int j = 0;
  public TestRemovePop getThis() {
    j = j + 1;
    return this;
  }
  public static void main(String[] args) {
    int x, y;
    x = y = 3;
    TestRemovePop p = new TestRemovePop();
    int t = p.getThis().i = 5;
    System.out.println(j);
    //System.out.println(t);
    //System.out.println(p.i);
    //System.out.println(p.getThis().i);
    long u = p.getThis().l = 5;
    System.out.println(j);
    int v = p.getThis().a[1] = 5;
    System.out.println(j);
  }
}
