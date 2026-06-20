// Type inference does not use the loop variable type as target type.
// https://bitbucket.org/extendj/extendj/issues/285/type-inference-error-for-enhanced-for-loop
// .result: COMPILE_FAIL
import java.util.*;
public class Test {
  void test() {
    // The inferred type of emptyList() is List<Object> which cannot be iterated over
    // with loop variable of type String.
    for (String s : Collections.emptyList()) {
    }
  }
}
