// .result=COMPILE_PASS
public class Test {
	interface A  {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B  {
		default void m(int i) {  }
	}
	
	public interface C extends A, B {
		default void m(int i) {
			A.super.m(4);
			B.super.m(3);
		}
	}
}