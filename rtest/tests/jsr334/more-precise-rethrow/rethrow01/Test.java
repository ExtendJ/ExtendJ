// .result=COMPILE_FAIL
class Test {
	class E1 extends Exception {}
	class E2 extends Exception {}
	{
		try {
			throw new E1();
		} catch (final Exception e) {
			try {
				throw e;
			} catch (E1 e1) {
			} catch (E2 e2) {
				// unreachable - Test.E2 never thrown
			}
		}
	}
}
