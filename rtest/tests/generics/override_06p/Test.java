// Instantiating a generic type does not induce duplicate method errors
// .result=COMPILE_PASS
class A<U> {
	void m(U u) { }
	void m(Number n) { }
}

class Test {
	A<Number> a = new A<Number>();
}
