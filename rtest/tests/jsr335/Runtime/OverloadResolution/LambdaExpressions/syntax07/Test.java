// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
  public static int out = 0;

  interface A {
    void m(int a, int b);
  }
  interface B {
    void m(String a, int b);
  }

  public static void method(A... as) {
    out = 1;
  }

  public static void method(B... bs) {
    out = 2;
  }

  public static void main(String[] args) {
    // Tests lambdas for phase 3 (applicable by variable arity)
    method((a, b) -> {}, (a, b) -> { }, (String a, int b) -> { }, (a, b) -> { });
    testTrue("Method overload", out == 2);

    // Tests lambdas for phase 3 (applicable by variable arity)
    method((a, b) -> {}, (int a, int b) -> { }, (a, b) -> { });
    testTrue("Method overload", out == 1);
  }
}
