public class TestInnerClass {
  public void test() {
    System.out.println("Enclosing");
  }
  static int field = 1;
  int field2 = 2;
  public String name() {
    return "LocalVariable";
  }
  public static void main(String[] args) {
    StaticInner inner = new StaticInner();
    final TestInnerClass localVariable = new TestInnerClass();
    class Local {
      public Local(final long l) {
        System.out.println("Local");
        System.out.println("Enclosing local variable " + localVariable.name());
        class LocalLocal {
          public LocalLocal() {
            System.out.println("LocalLocal enclosing local variable " + localVariable.name());
            System.out.println("Enclosing l " + l);
          }
        }
        new LocalLocal();
      }
    }
    Local l = new Local(5);
    Object o = new Object() {
      {
        System.out.println("Anonymous");
      }
    };
    new TestInnerClass().new Inner();
  }

  static class StaticInner {
    public StaticInner() {
      System.out.println("StaticInner");
      System.out.println("Enclosing static field " + field);
    }
  }

  class Inner {
    public Inner() {
      System.out.println("Inner");
      System.out.println("Enclosing field " + field2);
      test();
    }
  }
}
