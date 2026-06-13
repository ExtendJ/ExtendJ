// .result=COMPILE_FAIL
class A {
	void m() { }
}

class Test extends A {
	/* Test.m() not return-type substitutable for A.m() */
	int m() {
		return -1;
	}
}
