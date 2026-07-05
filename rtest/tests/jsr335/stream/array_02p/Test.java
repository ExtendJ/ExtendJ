// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;
public class Test {
  public static Stream<IntVar> getStream(Point[] scope) {
    return Arrays.stream(scope).map(r -> Arrays.stream(r.coords)).flatMap(i -> i);
  }
}

class IntVar {
}

class Point {
  public IntVar[] coords;
}
