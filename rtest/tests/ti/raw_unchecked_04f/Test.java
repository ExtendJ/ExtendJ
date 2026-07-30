// The unchecked conversion needed by the nested invocation of f carries into
// the inference of the enclosing invocation of head, whose result type is
// erased as well (JLS SE8 §18.5.1, §18.5.2).
// .result: COMPILE_FAIL
import java.util.List;

public class Test {
  static <U> List<U> f(List<U> l) {
    return l;
  }

  static <V> V head(List<V> l) {
    return l.get(0);
  }

  @SuppressWarnings("unchecked")
  void m(List raw) {
    String s = head(f(raw)); // V is not inferred as String.
  }
}
