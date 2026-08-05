// The type of a lambda parameter is the GLB of the parameter type
// of the inferred function type.
// See issue 181.
// .result=COMPILE_PASS
import java.util.function.Function;

public class Test {
  Function<? super String, Integer> fun = s -> s.length();
}
