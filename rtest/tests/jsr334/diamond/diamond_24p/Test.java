// Test for issue in type analysis.
// See issue 173.
// .result=COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    Set<String> hashSet01 = new HashSet<>();
    Set<String> hashSet02 = new HashSet<>();
    hashSet02.addAll(new ArrayList<>(hashSet01));
  }
}
