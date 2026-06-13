// .result=COMPILE_FAIL
interface I<T> {
	public void m(T t);
}
class Test implements I<String> {
	// I.m and Test.m have same erasure but Test.m does not override I.m
	public void m(Object s) {
	}
}
