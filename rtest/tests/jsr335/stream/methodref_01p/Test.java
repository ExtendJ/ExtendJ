// Test code generation error for wildcard parameterized method references.
// See issue 219.
// .result: EXEC_PASS
import java.util.*;

public class Test {
  public static void main(String[] args) {
    List<Integer> ints = Arrays.asList(7, 8, 9);
    ints.forEach(System.out::println);
  }
}
