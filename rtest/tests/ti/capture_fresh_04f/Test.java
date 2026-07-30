// Capture conversion is applied separately to each expression, so the two
// occurrences of the qualifier capture to two distinct variables and the
// result of get cannot be passed to add().
// .result: COMPILE_FAIL
import java.util.List;

public class Test {
  void m(List<?> l) {
    l.add(l.get(0));
  }
}
