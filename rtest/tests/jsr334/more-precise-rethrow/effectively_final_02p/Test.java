// .result=COMPILE_PASS
class Test {
	class E1 extends Exception { }
	void m() throws E1 {
		try {
			u();
		} catch (Exception e) {
			// statement not modifying e:
			e.getMessage();
			throw e;
		}
	}
	void u() throws E1 {
		throw new E1();
	}
}
