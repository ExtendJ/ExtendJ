// .result=COMPILE_FAIL
interface I {
	void m(int i);
}

interface J {
	int m(int i);
}

abstract class Test implements I, J {
	// incompatible return types for abstract methods
}
