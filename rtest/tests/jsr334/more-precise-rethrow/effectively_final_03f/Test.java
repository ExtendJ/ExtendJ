// .result=COMPILE_FAIL
class Test {
	class E1 extends Exception { }
	void m() throws E1 {
		try {
			u();
		} catch (Exception e) {
			// statement modifying e:
			boolean b = !(3 == w(e = new E1()));
			// e no longer effectively final so we can not assume
			// that e is instanceof E1 according to Java 7 specs
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
