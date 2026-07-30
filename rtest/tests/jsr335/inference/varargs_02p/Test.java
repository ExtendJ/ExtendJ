// Test both calling conventions of a generic variable arity method.
// A variable arity invocation matches the trailing arguments against the variable
// arity parameter's component type, while a fixed arity (as-array) invocation
// matches a single array argument against the array type. The two conventions give
// different formal parameter types, so each needs its own applicability bound set.
// .result=COMPILE_PASS
import java.util.Arrays;
import java.util.List;

public class Test {
  @SafeVarargs
  static <T> List<T> listOf(T... elements) {
    return Arrays.asList(elements);
  }

  public static void main(String[] args) {
    List<String> several = listOf("pam", "the");           // Variable arity: T = String.
    List<String> single = listOf("bird");                  // Variable arity: T = String.
    List<String> none = listOf();                          // Variable arity, T inferred from the target type.
    List<String> asArray = listOf(new String[] { "ptb" }); // Fixed arity: T = String.

    // Variable arity with array arguments: T = String[].
    List<String[]> arrays = listOf(new String[] { "e" }, new String[] { "f" });
  }
}
