public class TestConstantValueAttribute implements TestConstantValueAttributeExample {
  public static void main(String[] args) {
    System.out.println(TestConstantValueAttributeExample.CONST1);
    System.out.println(TestConstantValueAttributeExample.CONST2);
    System.out.println(TestConstantValueAttributeExample.CONST3);

    TestConstantValueAttributeExample e = new TestConstantValueAttribute();
    e.test();
  }
  public void test() {
    System.out.println("Test");
  }
  
}
interface TestConstantValueAttributeExample {
  static final int CONST1 = 5+4;
  static final String CONST2 = "hello" + " world";
  static final boolean CONST3 = 5 == 2;

  public abstract void test();
}
