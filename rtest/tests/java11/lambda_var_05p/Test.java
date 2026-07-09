// Test using lambda with 'var'-declared parameter inside stream expression.
import java.util.*;
public class Test {
  public static void main (String[] args) {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    numbers.stream()
      .map((var num) -> num * 2)
      .forEach(System.out::println);
  }
}
