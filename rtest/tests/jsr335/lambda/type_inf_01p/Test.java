// The type of a lambda parameter is the GLB of the parameter type
// of the inferred function type.
// See https://bitbucket.org/extendj/extendj/issues/181/lambda-parameters-do-not-use-the-glb-of
// .result=COMPILE_PASS
import java.util.stream.Stream;

public class Test {
  void m(Stream<String> stream) {
    // The lambda below has inferred type Function<? super String, R>.
    // The paramter s has the type String.
    stream.map(s -> s.length());
  }
}
