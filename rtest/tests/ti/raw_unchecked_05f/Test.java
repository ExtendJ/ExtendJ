// The result type of an invocation that needed unchecked conversion is erased,
// so the element type of the result is not inferred from the context (JLS SE8 §18.5.2).
// .result: COMPILE_FAIL
import java.util.List;

public class Test {
  static <U> List<U> f(List<U> l) {
    return l;
  }

  @SuppressWarnings("unchecked")
  void m(List raw) {
    for (String s : f(raw)) {
    }
  }
}
