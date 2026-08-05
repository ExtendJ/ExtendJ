// Conflicting single-static imports are OK if not used.
// See issue 227.
// .result: COMPILE_PASS
import static alfa.Alfa.Gamma;
import static beta.Beta.Gamma;
public class Test {
}
