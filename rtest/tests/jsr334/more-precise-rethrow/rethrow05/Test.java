// .result=COMPILE_PASS

class Test {

	class E1 extends Exception {}
	class E2 extends Exception {}
	void m() throws E1, E2 { 
		try {
			if (System.currentTimeMillis() % 2 == 1)
				throw new E1();
			else
				throw new E2();
		} catch (final Exception e) {
			throw e;
		}
	}

}
