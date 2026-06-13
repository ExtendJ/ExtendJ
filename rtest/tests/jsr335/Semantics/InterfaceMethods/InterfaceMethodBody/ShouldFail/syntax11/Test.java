// .result=COMPILE_FAIL
public class Test {
	interface A {
		static void m(int i) { System.out.println("" + i + 1); }
	}
	interface B {
		default void m(int i) {  }
	}
	
	public class C implements A, B {
		public void m() {
			A.super.m(5);
			m(3);
		}
	}
}