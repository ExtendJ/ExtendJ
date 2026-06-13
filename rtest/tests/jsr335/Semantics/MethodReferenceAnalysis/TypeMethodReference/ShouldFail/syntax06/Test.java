// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A { 
		void method(int i); 
	}
	
	public void m(int k) {
		
	}
	
	public void testMethod() {
		A a = int::m;
	}
}