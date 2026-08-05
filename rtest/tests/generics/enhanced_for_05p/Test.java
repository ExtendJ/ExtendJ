// Type inference does not use the loop variable type as target type.
// See issue 285.
// .result: COMPILE_FAIL
import java.util.*;
public class Test {
  void test() {
    for (Test t : myIterable()) {
    }
  }

  <U> Iterable<U> myIterable() {
    return null;
  }
}
