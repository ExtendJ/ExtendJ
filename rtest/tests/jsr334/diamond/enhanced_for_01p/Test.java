// Test that enhanced-for can iterate over a generic type created with diamond.
// See issue 285.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  void test(Collection<String> strs) {
    for (String s : new HashSet<>(strs)) {
      System.out.println(s);
    }
  }
}
