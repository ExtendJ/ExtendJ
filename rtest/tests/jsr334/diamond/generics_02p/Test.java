// Test that diamond works with an argument that has inferred type.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    Set<String> argSet = new HashSet<>(Collections.singleton("foo"));
  }
}
