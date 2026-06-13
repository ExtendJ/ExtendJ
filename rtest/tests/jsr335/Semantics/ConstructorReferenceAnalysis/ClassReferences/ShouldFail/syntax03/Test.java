// .result=COMPILE_FAIL
import java.util.ArrayList;


public class Test {
	
	/*
	 * This test case currently fails, the reason is a bug in JastAddJ.
	 * See issue #74 in the git bitbucket repository
	 */
	
	interface A1 {
		B m();
	}
	interface A2 {
		B m(String s);
	}
	
	class B {
		public B() {
			
		}
		public <T> B(T t) {
			
		}
	}
	
	public void testMethod() {
		A1 a1 = B::new;
		A2 a2 = B::<Integer>new;
	}

}