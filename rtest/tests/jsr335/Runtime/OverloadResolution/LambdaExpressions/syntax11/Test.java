// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
  public static int out = 0;

  interface A {
    double m(int a, int b);
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
    // Tests most specific method for lambdas, bullet #4
    method((int a, int b) -> args.length == 3 ? 6 : Integer.valueOf(2));
    testTrue("Method overload", out == 1);

    out = 0;
    // Tests most specific method for lambdas, bullet #4
    method((int a, int b) -> args.length == 3 ? Short.valueOf((short)6) : Integer.valueOf(2));
    testTrue("Method overload", out == 1);

    out = 0;
    // Tests most specific method for lambdas, bullet #5
    method((int a, int b) -> args.length == 3 ? Integer.valueOf(6) : Integer.valueOf(2));
    testTrue("Method overload", out == 2);
    }
}
