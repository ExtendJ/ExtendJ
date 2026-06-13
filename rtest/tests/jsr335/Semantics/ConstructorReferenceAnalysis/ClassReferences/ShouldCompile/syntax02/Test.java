// .result=COMPILE_PASS

public class Test {
	
	interface A1 {
		B m();
	}
	interface A2 {
		B m(int i);
	}
	
	class B {
		public B() {
			
		}
		public B(int i) {
			
		}
	}
	
	public void testMethod() {
		A1 a1 = B::new;
		A2 a2 = B::new;
	}

}