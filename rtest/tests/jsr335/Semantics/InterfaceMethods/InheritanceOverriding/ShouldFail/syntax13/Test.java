// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B extends A {
		default void m(int i) { A.super.m(i); }
	}
	
	interface C extends B {
		default void m(int i) { A.super.m(i); }
	}
	
	public static void main(String[] arg) {
		
	}
}