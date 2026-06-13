// Test overriding package private method as protected
// .result=COMPILE_PASS
class A {
	void m() {
	}
}
class Test extends A {
	protected void m() {
	}
}

