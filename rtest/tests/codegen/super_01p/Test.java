// Test an inner class constructor super-call with a conditional expression argument
import java.io.PrintStream;

public class Test {
	static boolean b = false;
	public static void main(String argv[]) {
		new Test().new Y();
	}
	class X {
		X (int i) {
		}
	}
	class Y extends X {
		Y() {
			super(Test.b ? 0xDEAD : 0xBEEF);
		}
	}
}
