// Variable-arity constructor invocation with arguments that need target-type
// inference. Constructor overload resolution previously
// failed to find a target type for the arguments, so no constructor matched.
// Fixed by adding BoundCtorAccess to test applicability per candidate.
// .result: EXEC_PASS
import java.util.Collections;
import java.util.List;

public class Test {
  Test(String first, List<String>... rest) {
    System.out.println(first + ":" + rest.length);
  }

  public static void main(String[] args) {
    new Test("rest", Collections.emptyList(), Collections.emptyList());
  }
}
