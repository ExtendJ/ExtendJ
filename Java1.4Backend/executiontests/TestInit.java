public class TestInit {
  public int f = 10;
  public int g;
  public long[] ints = { 1,(byte)2,(short)3 };
  {
    g = 3;
  }
  public TestInit() {
    super();
  }

  public TestInit(int i) {
    this();
    f = i;
  }
    
  public static void main(String[] args) {
    TestInit init = new TestInit();
    System.out.println(init.f);
    System.out.println(init.g);
    init = new TestInit(5);
    System.out.println(init.f);
    System.out.println(init.g);
    System.out.println(init.ints[1]);
  }
}
