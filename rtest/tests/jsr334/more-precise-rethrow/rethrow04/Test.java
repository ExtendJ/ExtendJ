// .result=COMPILE_FAIL

class Test {

	class E1 extends Exception {}
	class E2 extends Exception {}
	class E3 extends Exception {}
	{
		try {
			long a = System.currentTimeMillis();
			if (a % 2 == 0) {
				throw new E1();
			} else {
				throw new E2();
			}
		} catch (E1 | E2 e) {
			try {
				throw e;
			} catch (E1 e1) {
			} catch (E2 e2) {
			} catch (E3 e3) {
				// unreachable - Test.E3 not thrown
			}
		}
	}

}
