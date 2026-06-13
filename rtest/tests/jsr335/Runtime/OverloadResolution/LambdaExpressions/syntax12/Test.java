// Tests that when finding most specific method with variable arity parameters
// the the last parameter of the most specific method is a subtype of the
// trailing parameters of the other method.
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
  public static int out = 0;

  interface A {
    int m(int a, int b);
  }

  interface B {
    void m(int a, int b);
  }

  interface C extends B {

  }

  public static int m() {
    return 5;
  }

  public static void method(A a1, C... a2) {
    out = 1;
  }

  public static void method(B... i) {
    out = 2;
  }

  public static void main(String[] args) {
    // The method call below should pick the one with two formal parameters.
    method((int a, int b) -> m(), (int a, int b) -> m());
    testTrue("Method overload", out == 1);
  }
}
