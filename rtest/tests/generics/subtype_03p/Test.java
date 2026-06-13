// Test that a generic interface can be inherited as a raw interface
// from another raw interface declaration.
// .result=COMPILE_PASS
public class Test implements KeyIterable, Map.Entry {
}

interface KeyIterable<T> { }

interface Map<K, V> {
  public interface Entry<K, V> extends KeyIterable<K> {
  }
}
