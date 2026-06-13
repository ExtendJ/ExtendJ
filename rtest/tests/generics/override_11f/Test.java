// When inheriting methods from interfaces the implementing class must implement
// the correct parameterization of the method
// .result=COMPILE_FAIL
class Test implements C<Test> {
	public int m(Object o) {
		return 0;
	}
}

interface C<T extends C> extends I<T> {
	public int m(T o);
}

interface I<T> {
	public int m(T o);
}
