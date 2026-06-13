// Test for bug causing NullPointerException when a variable lookup fails.
// https://bitbucket.org/extendj/extendj/issues/202/method-lookup-error-causes
// .result: COMPILE_FAIL
import java.util.function.Function;

public interface Test {
  String transform(String in, Function<String, String> fun);

  default void prepend(String string) {
    transform(missing, t -> t);
  }
}


