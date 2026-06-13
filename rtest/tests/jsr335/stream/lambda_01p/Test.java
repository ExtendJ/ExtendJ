// Test a simple use of the Java 8 stream API with a lambda.
// This checks that a lambda with generic type is executable.
// https://bitbucket.org/extendj/extendj/issues/216
// .result: EXEC_PASS
import java.util.*;
import java.util.stream.Collectors;

public class Test {
  public static void main(String[] args) {
    Collection<Integer> c = new LinkedList<>();
    c.add(1);
    c.add(2);
    c.stream().map(e -> e).collect(Collectors.toList());
  }
}
