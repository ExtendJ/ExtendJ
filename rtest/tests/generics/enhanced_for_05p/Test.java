// Type inference does not use the loop variable type as target type.
// https://bitbucket.org/extendj/extendj/issues/285/type-inference-error-for-enhanced-for-loop
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
