// Overriding method from interface of abstract superclass with incompatible return type
// https://bitbucket.org/jastadd/jastaddj/issue/107/incompatible-return-type-for-method
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
