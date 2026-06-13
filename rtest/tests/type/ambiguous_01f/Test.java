// Ambiguous type access (import conflicts with java.lang.String)
// .result=COMPILE_FAIL
import pkg.A.*;

class Test {
	String s;// error: String is ambiguous
}
