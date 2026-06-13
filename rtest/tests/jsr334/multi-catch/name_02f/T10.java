// Redeclaration of catch parameters is not legal.
// .result: COMPILE_FAIL
public class Test {
	class E1 extends Exception {}
	class E2 extends Exception {}
	class E3 extends Exception {}
	public void foo() {
		try {
			if (System.currentTimeMillis() % 2 == 0)
				throw new E1();
			else
				throw new E2();
		} catch (E1 | E2 e) {
			try {
				switch ((int)(System.currentTimeMillis() % 3)) {
					case 0:
						throw new E1();
					case 1:
						throw new E2();
					case 2:
						throw new E3();
				}
			} catch (E1 | E2 | E3 e) {
			}
		}
	}
}
