// Test stackmap frames generation.
public class Test {
  public static void main(String[] args) {
    A a = new A();
    System.out.println(build(true, a).getClass().getSimpleName());
    System.out.println(build(false, a).getClass().getSimpleName());
  }

  static A build(boolean cond, A a) {
    return cond ? a : new B();
  }

  static class A {
  }

  static class B extends A {
  }
}
