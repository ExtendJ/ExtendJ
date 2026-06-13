// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public class Inner extends Test {
		public void method() {
			A a1 = super::method;
			A a2 = Inner.super::method;
		}
	}
	
	public int method(int a, String s) {
		return a + s.length();
	}
}