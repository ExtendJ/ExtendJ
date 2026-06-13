// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Test {
	
	interface A {
		B m(int i) throws IOException;
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