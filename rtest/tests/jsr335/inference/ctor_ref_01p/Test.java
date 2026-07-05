// Test target type inference for constructor reference.
// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  void run() {
    print(newTest(Test::new));
  }

  static void print(Test test) {
  }

  static <T> T newTest(Builder<T> builder) {
    return builder.build();
  }
}

interface Builder<T> {
  T build();
}
