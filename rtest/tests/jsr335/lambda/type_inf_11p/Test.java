// Test targetType attribute for cast expression.
// .result: COMPILE_PASS
import java.util.function.Function;

public class Test {
  // The cast is necessary for the inner lambda expression to have correct target type.
  Function<String, Object> svamp = agaricus -> (Function<String, String>) bisporus -> agaricus + bisporus;
}
