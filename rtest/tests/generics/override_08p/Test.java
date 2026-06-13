// Inheriting abstract and concrete method with same signature due to type parameterization
// .result=COMPILE_PASS
abstract class A<U> {
	void m(U u) { }
	abstract void m(Number n);
}

abstract class Test extends A<Number> {
	void m(Number n) { } // overrides and implements
}
