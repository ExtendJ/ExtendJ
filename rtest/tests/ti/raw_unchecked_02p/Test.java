// The erased result type of an invocation that needed unchecked conversion does
// not depend on the bound of the type parameter (JLS SE8 §18.5.2).
// See issue 345.
// .result: COMPILE_PASS
import java.util.List;

public class Test {
  static <U extends Number> List<U> f(List<U> l) {
    return l;
  }

  @SuppressWarnings("unchecked")
  void m(List raw) {
    List<String> l = f(raw);
  }
}
