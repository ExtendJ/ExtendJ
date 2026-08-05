// Overriding method from interface of abstract superclass with incompatible return type.
// See issue 107
// .result=COMPILE_FAIL

interface I {
	int m();
}

abstract class S implements I {
	public int m() {
		return 0;
	}
}

public class Test extends S {
	public byte m() {
		return 0;
	}
}
