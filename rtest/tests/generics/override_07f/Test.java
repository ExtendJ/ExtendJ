// Inheriting abstract and concrete method with same signature due to type parameterization
// .result=COMPILE_FAIL
abstract class A<U> {
	void m(U u) { }
	abstract void m(Number _);
}

abstract class Test extends A<Number> {
}
