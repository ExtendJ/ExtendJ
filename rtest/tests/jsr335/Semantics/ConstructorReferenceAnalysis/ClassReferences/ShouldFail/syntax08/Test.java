// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.io.*;


public class Test {
	
	interface A {
		B m() throws IOException;
	}
	
	class B {
		public B() throws Exception {
			
		}
		
		public B(int i) throws EOFException {
			
		}
	}
	
	public void testMethod() {
		A a = B::new;
	}

}