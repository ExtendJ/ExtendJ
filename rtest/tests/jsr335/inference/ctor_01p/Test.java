// Constructor overload resolution with an argument that needs target-type
// inference. The constructor applicability tests previously typed the
// argument against the enclosing (unresolved) invocation, so type inference
// found no target type and no constructor matched.
// Fixed by adding BoundCtorAccess to test applicability per candidate.
// .result: EXEC_PASS
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Test {
  Test(List<String> list) {
  }

  Test(Set<String> set) {
    throw new RuntimeException();
  }

  public static void main(String[] args) {
    new Test(Collections.emptyList());
  }
}
