// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A {
		List<Integer> m();
	}
	
	public void testMethod() {
		A a = ArrayList::new;
	}

}