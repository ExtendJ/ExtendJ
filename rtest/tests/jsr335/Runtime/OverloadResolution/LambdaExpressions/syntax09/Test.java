// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
  public static int out = 0;

  interface A {
    Number m(int a, int b);
  }
  interface B {
    Integer m(int a, int b);
  }

  public static void method(A a) {
    out = 1;
  }

  public static void method(B b) {
    out = 2;
  }

  public static void main(String[] args) {
    // Tests most specific method for lambdas, bullet #2
    method((int a, int b) -> Integer.valueOf(4));
    testTrue("Method overload", out == 2);
  }
}
