// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		default void m() { }
		int m2();
	}
	
	public int method1() {
		return 3;
	}
	
	public static void method2() {
		A a = super::method1;
	}
}