// Test overriding with parameterized return types, and different forms of parameterization.
// .result=COMPILE_PASS
interface Map<K, V> {
  public interface Entry<S, T> { }

  Set<Entry<K, V>> entrySet();
}

interface Set<T> {
}

abstract class AbstractMap<K, V> implements Map<K, V> {
  public abstract Set<Map.Entry<K, V>> entrySet();
}

public class Test<K, V> implements Map<K, V> {
  @Override
  public Set<Entry<K, V>> entrySet() {
    return null;
  }
}
