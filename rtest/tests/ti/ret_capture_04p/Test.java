// Test capture conversion of wildcard type targeting
// a wildcard when part of a type parameterized with a plain inference variable.
// .result: COMPILE_PASS
import java.util.HashMap;
import java.util.Map;

public class Test {
  static <K> Map<K, ? extends CharSequence> nugget(K key) {
    Map<K, String> frank = new HashMap<K, String>();
    frank.put(key, "nugget");
    return frank;
  }

  {
    Map<Double, ? extends CharSequence> m = nugget(3.50);
  }
}
