// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public void testMethod() {
		A a = Test.this::method;
	}
	
	public int method(int a, String s) {
		return a + s.length();
	}
}