// As bound_01f, but the unchecked conversion is implied by a declared bound
// mentioning two inference variables.
//
// Incorporating ‹Table <: α_M› with ‹α_M <: Table<α_K, α_V>› implies ‹Table <: Table<α_K, α_V>› (unchecked conversion).
//
// From Java 9 onwards the result type of the invocation is the erasure of V (§18.5.2).
// .result: COMPILE_FAIL
public class Test {
  static <M extends Table<K, V>, K, V> V get(M m, K k) {
    return m.get(k);
  }

  @SuppressWarnings("unchecked")
  void m(Table raw) {
    Val v = get(raw, new Key()); // V is not inferred as Val.
  }
}

interface Table<K, V> {
  V get(K key);
}

class Key { }

class Val { }
