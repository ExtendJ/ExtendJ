// Test conditional expression type analysis.
// .result=COMPILE_PASS
import java.util.Collection;
import java.util.List;

public class Test {
  Collection<? extends Number> test(boolean a, List<Integer> b, Collection<Integer> c) {
    return a ? b : c;
  }
}
