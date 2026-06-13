// Test that enhanced-for can iterate over a generic type created with diamond.
// https://bitbucket.org/extendj/extendj/issues/285/type-inference-error-for-enhanced-for-loop
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  void test(Collection<String> strs) {
    for (String s : new HashSet<>(strs)) {
      System.out.println(s);
    }
  }
}
