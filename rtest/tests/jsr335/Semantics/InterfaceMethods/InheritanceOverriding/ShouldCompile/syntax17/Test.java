// .result=COMPILE_PASS
public class Test {
	interface A extends B {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B  {
		default void m(int i) { /*A.super.m(i);*/ }
	}
	
	public interface C extends A, B {
		default void m(int i) {
			A.super.m(4);
		}
	}
}