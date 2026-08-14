// Two single-static imports of a field with the same name are ambiguous
// where the field is used.
// See issue 114.
// .result: COMPILE_FAIL
import static alfa.Alfa.gamma;
import static beta.Beta.gamma;

public class Test {
  int i = gamma; // Error: gamma is imported from both Alfa and Beta.
}
