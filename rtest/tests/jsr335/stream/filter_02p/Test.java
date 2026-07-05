// Test filtering an array.
import java.util.stream.*;
import java.util.*;

public class Test {
  public static void main(String[] args) {
    String data = "  , x,y also,";
    Arrays.stream(data.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(s -> s + " marks the spot")
        .forEach(System.out::println);
  }
}
