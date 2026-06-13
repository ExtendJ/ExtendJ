// Test assigning weaker access to implemented interface method
// .result=COMPILE_FAIL
interface I {
	void m();
}
class A {
	private void m() {
	}
}
class Test extends A implements I {
	private void m() {// error: weaker access privileges
	}
}

