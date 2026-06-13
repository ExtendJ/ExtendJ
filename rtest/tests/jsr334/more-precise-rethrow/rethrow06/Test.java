// .result=COMPILE_PASS

class Test {

	class E1 extends Exception {}
	void m() throws E1 { 
		try {
			throw new E1();
		} catch (Exception e) {
			throw e;
		}
	}

}
