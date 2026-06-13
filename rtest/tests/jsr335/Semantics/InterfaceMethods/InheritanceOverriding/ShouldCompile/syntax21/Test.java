// .result=COMPILE_PASS
public class Test {
	interface A {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B {
		default void m(int i) {  }
	}
	
	interface C {
		void m(int i);
	}
	
	class D implements A, B, C {
		public void m(int i) {
			
		}
	}
}