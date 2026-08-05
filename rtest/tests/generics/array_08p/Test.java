// Test for bug in ExtendJ code generation.
// See issue 261.
import java.util.Set;
import java.util.Collections;

public class Test {
  public static void main(String[] args) {
    foo(Collections.singleton("x marks the spot"));
  }

  static void foo(Set<String> set) {
    for (String msg : set.toArray(new String[set.size()])) {
      System.out.println(msg);
    }
  }
}
