// Overriding method with abstract superclass method having same name
// .result=COMPILE_FAIL

class S1 {
	public int m() {
		return 0;
	}
}

abstract class S2 extends S1 {
	public int m() {
		return 0;
	}
}

public class Test extends S2 {
	public byte m() {
		return 0;
	}
}
