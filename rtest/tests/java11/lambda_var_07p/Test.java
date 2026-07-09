// .result=EXEC_PASS
import java.util.*;
public class Test {
  public static void main (String[] args) {
    List<String> names = Arrays.asList("Banan", "Melon", "Kiwi", "Citron");
    names.stream()
      .filter((var name) -> name.charAt(name.length() - 2) == 'o')
      .map((var name) -> name.toUpperCase())
      .sorted()
      .forEach(System.out::println);
  }
}
