// Test that a method inherited from a raw superclass can override
// a method from a super interface.
// .result=COMPILE_PASS
class Test extends HashTable implements Table {
}

class HashTable<K, V> {
  public V remove(K key) {
    return null;
  }

  public V get(Object key) {
    return null;
  }
}

interface Table {
  Object remove(Object key);
  Object get(Object key);
}
