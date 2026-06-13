// .result=COMPILE_PASS
class Test {

	class E1 extends Exception {}
	class E2 extends Exception {}
	public void m() throws Exception {
		try {
			throw new E1();
		} catch (Exception e) {
			try {
				throw e;
			} catch (E1 e1) {
			} catch (E2 e2) {
				// reachable
			}
			// even though this assignment is
			// after the try block where e is rethrown
			// the assignment will make e non-final
			e = new E1();
		}
	}

}
