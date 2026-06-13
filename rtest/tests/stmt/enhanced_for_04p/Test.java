// Test that wildcard extends works with enhanced for statements.
// .result=COMPILE_PASS
import java.util.List;
class Test {
  void f(List<? extends Number> nums) {
    for (Number n : nums) {
    }
  }
}
