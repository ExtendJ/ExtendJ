// According to JLS 15.12.2.1 it is allowed to specify type arguments to non-generic method calls
// https://bitbucket.org/jastadd/jastaddj/issue/104/allow-type-arguments-on-invocation-of-non
// .result=COMPILE_PASS
class Test {
	void a() { }
	void b() {
		this.<Test>a();
	}
}
