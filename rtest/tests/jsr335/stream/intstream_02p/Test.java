// ExtendJ generates two anonymous classes to implement the lambdas in this test.
// The second implicit class has strange bytecode:
// https://bitbucket.org/extendj/extendj/issues/286/strange-bytecode-generated-for-anonymous
import java.util.*;
import java.util.stream.*;
public class Test {
  public static void main(String[] args) {
    String[] names = { "Alice", "Bob", "Acorn" };
    IntStream ints = strLen(Arrays.asList(names));
    ints.forEach(i -> System.out.println(i));
  }

  static IntStream strLen(List<? extends String> names) {
    return names.stream().mapToInt(name -> name.length());
  }
}
