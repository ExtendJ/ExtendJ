// The effective type of a union-type catch parameter is the least upper bound
// of the union types.
// .result: COMPILE_PASS
public class Test {
	public void pass() {
		try {
			if (System.currentTimeMillis() % 2 == 0)
				throw new E1();
			else
				throw new E2();
		} catch (E1 | E2 e) {
			// Catch parameter e has effective type MyException.
			e.a = 0xDeadBeef;
		}
	}
}

class MyException extends Exception {
	public int a;
}
class E1 extends MyException {}
class E2 extends MyException {}
