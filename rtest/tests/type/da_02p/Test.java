// Test that a blank final instance variable is seen as definitely assigned
// in a subclass constructor.
// .result=COMPILE_PASS
public class Test extends A{
  final int t;

  public Test() {
    t = a;
  }
}

class A {
  public static final int a;
  static {
    a = 3;
  }
}
