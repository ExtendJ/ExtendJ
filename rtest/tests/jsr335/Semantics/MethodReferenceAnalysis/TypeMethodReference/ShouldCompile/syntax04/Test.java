// .result=COMPILE_PASS

public class Test {
	
	interface A { void method(); }
	
	public static void m() {
		
	}
	
	A a = Test::m;
}