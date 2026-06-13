// Test running a lambda with inferred type.
// .result: EXEC_PASS
import java.util.function.Function;

public class Test {
  public static void main(String[] args) {
    map(1, e->e);
  }

  static <T, R> R map(T e, Function<? super T, R> f) {
    return f.apply(e);
  }
}
