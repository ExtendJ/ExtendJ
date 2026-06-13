// This is a variant of override_02f with a custom interface
// .result=COMPILE_FAIL
class Test extends C<Test> {
	public int m(Object o) {
		return 0;
	}
}

class C<T extends C> implements I<T> {
	public int m(T o) {
		return 1;
	}
}

interface I<T> {
	public int m(T o);
}
