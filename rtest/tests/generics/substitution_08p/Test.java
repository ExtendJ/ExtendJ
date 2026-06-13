// Test recursive type substitution.
// .result=COMPILE_PASS
public class Test extends Map<Integer> {
}

// Recursive type lookup.
class Map<K> {
  private Map.EntryIterator entries;

  private class EntryIterator extends PrivateIterator<K> { }

  private class KeyIterator extends PrivateIterator<K> { }

  private class PrivateIterator<T> {
    PrivateIterator() { }
    PrivateIterator(Map.EntryIterator i) {
    }
  }

  private class DescendingIterator extends PrivateIterator<K> { }
}
