// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A {
		List<Integer>[][] m(int i);
	}
	
	public static void method(A a) {
		
	}
	
	public static void main(String[] arg) {
		method((A)ArrayList[][]::new);
	}

}