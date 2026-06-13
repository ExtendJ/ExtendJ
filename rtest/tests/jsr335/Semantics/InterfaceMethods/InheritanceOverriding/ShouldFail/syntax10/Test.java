// .result=COMPILE_FAIL
public class Test {
	
	interface A {
		static void m(int i) { }
	}
	
	class B implements A { }
	
	public void someMethod() {
		B.m();
	}
}