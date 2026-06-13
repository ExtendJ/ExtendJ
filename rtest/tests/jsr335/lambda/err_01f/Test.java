// Test for NullPointerException for variable lookup error related to lambdas.
// https://bitbucket.org/extendj/extendj/issues/202/method-lookup-error-causes
// .result: COMPILE_FAIL
import java.util.function.Function;

interface StringTransformer {
  String transform(String str, Function<String, String> fun);
  String replaceWords(String str, Function<String, String> fun);
}

public class Test {
  public String replace(String input, StringTransformer transformer) {
    return transform(input, str -> replaceWords(str, word -> "foo"));
  }
}
