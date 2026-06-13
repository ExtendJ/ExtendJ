// .result=COMPILE_PASS

public class Test {
	
	interface A1 {
		B m();
	}
	interface A2 {
		B m(String s);
	}
	
	class B {
		public B() {
			
		}
		public <T> B(String s) {
			
		}
	}
	
	public void testMethod() {
		A1 a1 = B::new;
		A2 a2 = B::<Integer>new;
	}

}