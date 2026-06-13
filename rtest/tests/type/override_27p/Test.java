// Overriding method with incompatible return type in abstract class
// .result=COMPILE_PASS

class S {
	public int m() {
		return 0;
	}
}

abstract class Test extends S {
	// return type conflict not a problem: Test is abstract
	public int m() {
		return 0;
	}
}
