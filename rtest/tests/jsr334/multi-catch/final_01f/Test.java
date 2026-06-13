// The exception parameter of a multi-catch clause is implicitly final.
// .result: COMPILE_FAIL
public class Test {
	class E1 extends Exception {}
	class E2 extends Exception {}
	public void foo() {
		try {
			if (System.currentTimeMillis() % 2 == 0)
				throw new E1();
			else
				throw new E2();
		} catch (E1 | E2 e) {
			e = new Exception();
		}
	}
}
