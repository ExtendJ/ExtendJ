// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(int d);
	}
	
	public long method(int i) {
		return i;
	}
	
	public void testMethod() {
		Test t = new Test();
		A a = t::method;
	}
}