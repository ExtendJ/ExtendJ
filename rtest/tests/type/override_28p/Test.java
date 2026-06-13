// Overriding method with incompatible return type in abstract class
// .result=COMPILE_PASS

abstract class S {
	public int m() {
		return 0;
	}
}

class Test extends S {
	// return type conflict not a problem: S is abstract
	public int m() {
		return 0;
	}
}
