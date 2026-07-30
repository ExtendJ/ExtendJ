// A raw argument to a diamond constructor makes the class instance creation
// need unchecked conversion (JLS SE8 §18.5.2).
// See issue 345.
// .result: COMPILE_PASS
import java.util.HashMap;
import java.util.Map;

public class Test {
  @SuppressWarnings("unchecked")
  void m(Map raw) {
    Map<String, Integer> m = new HashMap<>(raw);
  }
}
