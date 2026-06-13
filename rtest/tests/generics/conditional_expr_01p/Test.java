// Test conditional expression type analysis.
// .result=COMPILE_PASS
import java.util.Collection;
import java.util.List;

public class Test {
  Collection<Integer> test(boolean a, List<Integer> b, Collection<Integer> c) {
    return a ? b : c;
  }
}
