// Multiple static imports of field with the same name
// .result=COMPILE_FAIL
import alfa.Alfa.gamma;
import beta.Beta.gamma;
public class Test {
	int i = gamma;
}
