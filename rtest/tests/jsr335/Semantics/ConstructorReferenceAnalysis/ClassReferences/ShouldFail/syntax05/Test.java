// .result=COMPILE_FAIL
import java.util.ArrayList;


public class Test {
	
	interface A {
		ArrayList<Integer> m(long i);
	}
	
	public void testMethod() {
		A a = ArrayList::new;
	}

}