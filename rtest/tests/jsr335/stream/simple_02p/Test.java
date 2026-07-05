import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    String[] hi = { "u4hed", "723" };
    print(Stream.concat(Arrays.stream(hi), Stream.of("pd8")));
  }

  static void print(Stream<Object> strm) {
    StringBuilder sb = new StringBuilder();
    strm.forEach(sb::append);
    System.out.println(sb.toString());
  }
}
