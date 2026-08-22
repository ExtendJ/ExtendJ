// Test a nested invocation of the same generic method whose type arguments are being
// inferred. Both invocations use the same inference variables, so the nested
// bound set must not be lifted into the enclosing one.
// .result=EXEC_PASS
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
  static <E> E head(List<E> list) {
    return list.get(0);
  }

  static <E> List<E> wrap(E element) {
    return Collections.singletonList(element);
  }

  public static void main(String[] args) {
    List<List<String>> lists = Arrays.asList(Arrays.asList("pam", "the"));
    String first = head(head(lists));
    List<List<String>> wrapped = wrap(wrap("bird"));
    System.out.println(first);
    System.out.println(wrapped);
  }
}
