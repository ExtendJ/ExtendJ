// Conflicting single-static imports are not OK if they are used.
// .result: COMPILE_FAIL
import static alfa.Alfa.Gamma;
import static beta.Beta.Gamma;
public class Test {
  Gamma g; // Error: ambiguous type Gamma.
}
