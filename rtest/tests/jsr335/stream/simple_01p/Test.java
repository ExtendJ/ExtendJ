import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    print(Stream.of("Sp", "bywVu", "dzOc"));
  }

  static void print(Stream<Object> strm) {
    StringBuilder sb = new StringBuilder();
    strm.forEach(sb::append);
    System.out.println(sb.toString());
  }
}
