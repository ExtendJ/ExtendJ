// It is an error if an concrete inherited method has same signature to another concrete inherited method (JLS SE7 8.4.8.4)
// .result=COMPILE_FAIL
class A<U> {
	void m(U u) { }
	void m(Number _) { }
}

class Test extends A<Number> {
}
