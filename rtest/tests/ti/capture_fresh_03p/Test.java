// The capture variables of the two wildcards of Map<?, ?> are distinct, but
// they are the same on both sides of the applicability check, so K and V are
// inferred as the two capture variables of the argument.
// .result: COMPILE_PASS
import java.util.Map;

public abstract class Test {
  abstract <K, V> void h(Map<K, V> m);

  void m(Map<?, ?> map) {
    h(map);
  }
}
