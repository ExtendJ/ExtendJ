// Type inference does not use the loop variable type as target type.
// See issue 285.
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
