// Multi-catch and try-with-resources.
// .result: COMPILE_PASS
public class Test {
	class E1 extends Exception {}
	class E2 extends Exception {}
	public void foo() {
		try (java.io.PrintStream resource = System.out) {
			if (System.currentTimeMillis() % 2 == 0)
				throw new E1();
			else
				throw new E2();
		} catch (E1 | E2 e) {
		}
		try (java.io.InputStream resource = System.in) {
			switch ((int)(System.currentTimeMillis() % 3)) {
				case 0:
					throw new E1();
				case 1:
					throw new E2();
			}
		} catch (E1 | E2 | java.io.IOException e) {
		}
	}
}
