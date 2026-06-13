// Test an inner class constructor super-call with a conditional expression argument
// .result=COMPILE_PASS
import java.io.PrintStream;

public class Test extends X.Y {
	static boolean z = false;
	Test(X b) {
		b.super(z ? 0xDEAD : 0xBEEF);
	}
}

class X {
	class Y {
		Y (int i) {
		}
	}
}
