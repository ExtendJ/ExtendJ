// .result=COMPILE_PASS

public class Test {
	
	interface A { 
		void method(int a, String b); 
	}
	
	public static String m(long a, String b) {
		return null;
	}
	
	public void testMethod() {
		A a = Test::m;
	}
}