// .result=COMPILE_FAIL

//Taken from the JSR335 part 15.28.1
class C {
	interface Fun<T,R> { R apply(T arg); }
	int size() { return 3; }
	static int size(C arg) { return arg.size(); }

	void test() {
		Fun<C, Integer> f1 = C::size; // c.size() or C.size(c)?
	}
}