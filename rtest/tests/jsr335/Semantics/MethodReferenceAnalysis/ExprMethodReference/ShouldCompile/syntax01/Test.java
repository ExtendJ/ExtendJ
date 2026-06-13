// .result=COMPILE_PASS

public class Test {
	
	interface A {
		default void m() { }
		int m2();
	}
	
	public int method1() {
		return 3;
	}
	
	A a = this::method1;
}