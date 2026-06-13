// Return type conflict in overriding method
// .result=COMPILE_PASS

interface I {
	int m();
}

abstract class S implements I {
	// return type non-conflict: m() must not implement I.m()!
	public int m() {
		return 0;
	}
}

