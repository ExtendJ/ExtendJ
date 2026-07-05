// Test calling a variable arity method using an inferred argument type.
// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  void run() {
    printVars(emptyArray(String[]::new));
  }

  static void printVars(String... vars) {
  }

  static <T> T[] emptyArray(ArrayBuilder<T> builder) {
    return builder.build(0);
  }
}

interface ArrayBuilder<T> {
  T[] build(int size);
}
