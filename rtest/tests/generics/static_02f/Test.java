// It is not possible to refer to a static type using a parameterized type
// qualifier expression.
// .result=COMPILE_FAIL
interface Map<K, V> {
  public interface Entry<K, V> { }
}

public class Test {
  <K, V> Map.Entry<K, V> test(Map<K,V>.Entry<K, V> entry) { // Error: can't Entry<K, V> is static.
    return entry;
  }
}
