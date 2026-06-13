// .result=COMPILE_FAIL


public class Test {
	interface A {
		default void m(int i) {  }
	}
	
	
	class G implements A {
		public void method(int i) {
			A.m(i);
		}
	}
}