// Test assigning weaker access to implemented interface method
// .result=COMPILE_FAIL
interface I {
	void m();
}
class A {
	protected void m() {
	}
}
class Test extends A implements I {
	protected void m() {// error: weaker access privileges
	}
}

