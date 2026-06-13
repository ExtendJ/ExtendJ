// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A { 
		void method(int i); 
	}
	
	public void m(int k) {
		
	}
	
	public class Inner extends Test {
		
	}
	
	public void testMethod() {
		Inner i = new Inner();
		A a = i::m;
	}
}