// .result=COMPILE_PASS
class Test {
	class E1 extends Exception { }
	void m() throws E1 {
		try {
			u();
		} catch (Exception e) {
			// statement not modifying e:
			boolean b = !(3 == w(e));
			throw e;
		}
	}
	void u() throws E1 {
		throw new E1();
	}
	int w(Object o) {
		return o.hashCode();
	}
}
