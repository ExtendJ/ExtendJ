// Test that the type argument of a diamond in an argument position is inferred
// from the argument of the class instance creation, not from its target type.
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
