// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(int p1, int p2, String p3);
	}
	
	public int method(int p1, int p2) {
		return p1 + p2;
	}
	
	public void testMethod() {
		Test t = new Test();
		A a = t::method;
	}
}