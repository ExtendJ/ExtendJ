// Test return type inference.
// https://bitbucket.org/extendj/extendj/issues/217/failure-in-lambda-return-type-inference
// .result: COMPILE_PASS
import java.util.function.Function;

public class Test {
  public static void main(String[] args) {
    int res = map(1, e->e);
  }

  static <T, R> R map(T e, Function<T, R> f) {
    return f.apply(e);
  }
}
