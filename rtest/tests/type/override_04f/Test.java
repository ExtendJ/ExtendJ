// .result=COMPILE_FAIL
abstract class A {
	abstract public void m();
}

interface I {
	int m();
}

public class Test extends A implements I {
	public int m() {// conflicts with A.m()
		return 0;
	}
}
