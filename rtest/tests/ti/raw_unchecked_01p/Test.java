// Unchecked conversion is necessary to make f applicable, so the result type of
// the invocation is the erasure of the declared result type (JLS SE8 §18.5.2)
// and the assignment is an unchecked assignment rather than a type error.
// See issue 345.
// .result: COMPILE_PASS
import java.util.List;

public class Test {
  static <U> List<U> f(List<U> l) {
    return l;
  }

  @SuppressWarnings("unchecked")
  void m(List raw) {
    List<String> l = f(raw);
  }
}
