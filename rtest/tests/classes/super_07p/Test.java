// https://bitbucket.org/extendj/extendj/issues/234/broken-constructor-signature-for-anonymous
public final class Test {
  public static void main(String[] args) {
    new Test().run();
  }

  private abstract class AbstractTest {
    public abstract void test();
  }

  public void run() {
    new AbstractTest() {
      public void test() {
        System.out.println("x marks the spot");
      }
    }.test();
  }
}
