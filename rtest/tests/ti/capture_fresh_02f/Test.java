// Each wildcard type argument captures to its own type variable,
// so Map<?, ?> captures to Map<capture#1, capture#2> and no single T can be inferred.
// .result: COMPILE_FAIL
import java.util.Map;

public abstract class Test {
  abstract <T> void h(Map<T, T> m);

  void m(Map<?, ?> map) {
    h(map);
  }
}
