// With the new type inference in Java 8, a diamond constructor can have
// inferred-type arguments that rely on the target type, including other
// diamond expressions.
// See issue 267.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    Set<String> argSet = new HashSet<>(Collections.emptyList());
  }
}
