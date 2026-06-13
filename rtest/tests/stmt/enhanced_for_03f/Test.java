// The enhanced for expression type must be assignment compatible to the
// parameter type.
// .result=COMPILE_FAIL
import java.util.List;
class Test {
  void f(List<Number> nums) {
    for (Integer i : nums) { // Error: Number not assignable to Integer.
    }
  }
}
