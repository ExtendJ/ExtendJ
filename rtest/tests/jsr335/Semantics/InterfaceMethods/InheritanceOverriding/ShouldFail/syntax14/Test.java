// .result=COMPILE_FAIL
public class Test {
	interface A  {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B extends A {
		default void m(int i) {  }
	}
	
	public interface C extends A, B {
		default void m(int i) {
			A.super.m(4);
		}
	}
}