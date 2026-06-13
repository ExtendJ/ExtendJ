// Multiple methods with override-equivalent signatures
// .result=COMPILE_FAIL
class Test<T> {
	void m(Object _) {
	}
	void m(T _) {
	}
}
