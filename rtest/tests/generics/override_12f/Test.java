// When inheriting methods from interfaces the implementing class must implement
// the correct parameterization of the method
// .result=COMPILE_FAIL
abstract class Test implements I<Test> {
	// should implement m(Test)!
	public int m(Object o) {
		return 0;
	}
}

interface I<T> {
	public int m(T o);
}
