// With the new type inference in Java 8, a diamond constructor can have
// inferred-type arguments that rely on the target type, including other
// diamond expressions.
// This tests checks that an argument to a diamond consturctor access can use
// diamond access.
// https://bitbucket.org/extendj/extendj/issues/266/stack-overflow-caused-by-nested-diamond
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  void pass() {
    Set<String> argSet = new HashSet<>(new ArrayList<>());
  }
}
