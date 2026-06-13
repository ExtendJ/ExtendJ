// Wrong bytecode generated for call to static interface method.
// https://bitbucket.org/extendj/extendj/issues/220/intstream-and-doublestream-does-not-work
// .result: EXEC_PASS
import java.util.*;
import java.util.stream.*;
public class Test {
  public static void main(String[] args) {
    IntStream.range(0, 56).boxed().collect(Collectors.toSet());
  }
}
