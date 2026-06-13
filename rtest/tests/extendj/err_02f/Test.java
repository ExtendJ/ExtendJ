// .result=COMPILE_ERR_OUTPUT
class Test {
	void f(double d) { }
	void m() { f(f(0.1)); }
}
