// Test calling an overridden method in a raw type.
// .result=COMPILE_PASS
import java.util.HashMap;

public class Test {
  @SuppressWarnings("unchecked")
  void test(HashMap map, String key, Integer value) {
    map.put(key, value);
  }
}
