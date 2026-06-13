// .result=COMPILE_PASS
import java.util.ArrayList;


public class Test {
	
	interface A {
		ArrayList<Integer> m(short i);
	}
	
	public void testMethod() {
		A a = ArrayList<Integer>::new;
	}

}