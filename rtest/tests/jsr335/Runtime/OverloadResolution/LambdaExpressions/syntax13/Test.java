// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
  public static int out = 0;

  public static void method(Integer a, Integer... i) {
    out = 1;
  }

  public static void method(Number n1, Number n2, Number n3, Integer... i) {
    out = 2;
  }

  public static void main(String[] args) {
    Integer n1 = 3, n2 = 2, n3 = 4;
    method(n1, n2, n3);
    testTrue("Method overload", out == 1);
  }
}
