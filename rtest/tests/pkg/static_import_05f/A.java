package pkg;

public class A {
  public static class B extends A {
  }

  public static B newB() {
    return new B();
  }
}
