// Return type conflict in overriding method
// .result=COMPILE_FAIL

interface I {
	int m();
}

abstract class S implements I {
	public int m() {
		return 0;
	}
}

abstract class Test extends S {
	// return type conflict: m() must implement I.m()
	public byte m() {
		return 0;
	}
}
