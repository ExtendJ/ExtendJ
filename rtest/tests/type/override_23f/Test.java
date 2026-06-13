// Multiple methods with override-equivalent signatures
// .result=COMPILE_FAIL
class Test {
	void m(Object _) {
	}
	int m(Object _) {
		return 0;
	}
}
