// A generic variable arity invocation used as a nested poly expression argument,
// both of a fixed arity generic method and of a variable arity invocation of the
// same generic method.
// .result=EXEC_PASS
import java.util.Arrays;
import java.util.List;

public class Test {
  @SafeVarargs
  static <T> List<T> listOf(T... elements) {
    return Arrays.asList(elements);
  }

  static <E> E head(List<E> list) {
    return list.get(0);
  }

  public static void main(String[] args) {
    String first = head(listOf("p", "a"));
    List<String> firstList = head(listOf(listOf("m"), listOf("t")));
    List<List<String>> lists = listOf(listOf("h"), listOf("e"));
  }
}
