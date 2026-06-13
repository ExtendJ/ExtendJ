// Test for bug in ExtendJ code generation.
// https://bitbucket.org/extendj/extendj/issues/261/broken-bytecode-for-enhanced-for-loop-with
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
