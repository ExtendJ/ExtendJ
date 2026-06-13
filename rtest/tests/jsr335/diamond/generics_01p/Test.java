// With the new type inference in Java 8, a diamond constructor can have
// inferred-type arguments that rely on the target type, including other
// diamond expressions.
// https://bitbucket.org/extendj/extendj/issues/267/diamond-constructor-inference-fails-if
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    Set<String> argSet = new HashSet<>(Collections.emptyList());
  }
}
