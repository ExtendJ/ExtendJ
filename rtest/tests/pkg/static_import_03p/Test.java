// Conflicting single-static imports are OK if not used.
// https://bitbucket.org/extendj/extendj/issues/227/error-should-not-be-generated-for-unused
// .result: COMPILE_PASS
import static alfa.Alfa.Gamma;
import static beta.Beta.Gamma;
public class Test {
}
