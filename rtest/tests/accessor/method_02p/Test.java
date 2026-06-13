// Inner classes can circumvent access restrictions to access other sibling inner
// classes' private fields and methods via accessor methods.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    new Test().new A();
  }

  private class A {
    public A() {
      new B().hidden();
    }
  }

  protected class B {
    private void hidden() {
      // Can be called from A despite being declared private.
    }
  }
}
