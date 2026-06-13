// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m();
	}
	
	public int method(int i) {
		return i;
	}
	
	A a = this::method;
}