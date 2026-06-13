// Unboxing conversion for generic typed argument
// .result=COMPILE_PASS
class Test {
	<T extends Integer> void f(T x) {
		g(x);
	}

	void g(int i) { }
}
