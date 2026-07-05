import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    String[] vars = { "piti", "piw", "piw", "wiw", "wiw" };
    asStream(vars).forEach(System.out::println);
  }

  static Stream<String> asStream(String[]... vars) {
    return Arrays.stream(vars).map(Arrays::stream).flatMap(x -> x);
  }
}
