// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
	static class E1 extends Exception {}
	static class E2 extends Exception {}
	public static void main(String[] args) {
		for (int i = 0; i < 2; ++i) {
			try {
				if (i % 2 == 0)
					throw new E1();
				else
					throw new E2();
			} catch (E1 | E2 e) {
			} catch (Throwable e) {
				fail("unexpected exception type caught: " +
						e.getClass().getName());
			}
		}
	}
}
