// Test missing enclosing instance error message
// https://bitbucket.org/jastadd/jastaddj/issue/96/missing-enclosing-instance-for-class
// .result=COMPILE_ERR_OUTPUT
class Test {
	void m () {
		C.D d = new C.D();
	}
}
class C {
	class D { }
}
