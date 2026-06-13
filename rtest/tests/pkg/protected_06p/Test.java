// Test using protected inner class constructor inside same package.
// .result=COMPILE_PASS
class Test extends A {
	A.X x = new A.X();
}

class A {
	protected class X {
	}
}
