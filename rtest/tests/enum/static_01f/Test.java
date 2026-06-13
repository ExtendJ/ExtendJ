// Can not, in a static enum, directly instantiate inner class of enclosing type.
// .result=COMPILE_FAIL
class Test {
	enum E {
		;
		D d = new D();// error
	}
	class D {
	}
}
