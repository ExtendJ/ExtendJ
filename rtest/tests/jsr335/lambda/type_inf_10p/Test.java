// Test targetType attribute for cast expression.
// .result: COMPILE_PASS
import java.util.function.Function;

public class Test {
  // The cast is necessary for the lambda expression to have correct target type.
  // Without the cast we would not have a functional interface for the lambda.
  Object svamp = (Function<String, Integer>) bisporus -> bisporus.length();
}
