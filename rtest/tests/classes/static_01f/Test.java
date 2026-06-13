// Can not, in a static class, directly instantiate inner class of enclosing type.
// .result=COMPILE_FAIL
class Test {
	static class C {
		D d = new D();// error
	}
	class D {
	}
}
