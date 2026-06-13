// .result=COMPILE_FAIL
import java.util.ArrayList;


public class Test {
	
	interface A {
		int m();
	}
	
	public void testMethod() {
		A a = int::new;
	}

}