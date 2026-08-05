// Test filtering a set.
// See issue 221.
import java.util.stream.*;
import java.util.*;

public class Test {
  public static void main(String[] args) {
    Set<Integer> y = new HashSet<Integer>();
    y.add(3);
    y.add(5);
    y.add(-1);
    y.add(2938);
    y = y.stream().filter(c -> c < 3).collect(Collectors.toSet());
    System.out.println(y);
  }

}

