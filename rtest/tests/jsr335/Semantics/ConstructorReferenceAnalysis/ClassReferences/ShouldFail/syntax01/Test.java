// .result=COMPILE_FAIL
import java.util.ArrayList;


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
	
	public static void main(String[] arg) {
		A1 a = B::new;
	}

}