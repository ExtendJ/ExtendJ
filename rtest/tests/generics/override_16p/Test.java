// Test calling an overridden method in a raw type.
// .result=COMPILE_PASS
interface Map<K, V> {
  V put(K key, V value);
}

abstract class AbstractMap<K, V> implements Map<K, V> {
  interface InnerI<K, V> {
  }
  public class InnerC<K, V> {
  }
}

class MapImpl<K, V> extends AbstractMap<K, V> implements Map<K, V> {
  public V put(K key, V value) {
    return null;
  }
}

public class Test {
  @SuppressWarnings("unchecked")
  void test(MapImpl map, String key, Integer value) {
    map.put(key, value);
  }
}
