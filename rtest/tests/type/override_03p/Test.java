// .result=COMPILE_PASS
class A {
	void m(float f) { }
}

class Test extends A {
	/* Test.m() does not override A.m() */
	int m(int x) {
		return -1;
	}
}
