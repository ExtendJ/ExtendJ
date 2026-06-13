// .result=COMPILE_PASS

public class Test {
	
	interface A { 
		int method(B b, int i, String s); 
	}
	
	interface B {
		int method(int i, String s);
	}
	
	public void testMethod() {
		A a = B::method;
	}
}