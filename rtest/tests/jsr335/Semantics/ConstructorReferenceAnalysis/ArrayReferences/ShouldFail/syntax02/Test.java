// .result=COMPILE_FAIL
import java.util.ArrayList;


public class Test {
	
	interface A {
		ArrayList<Integer>[][] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = ArrayList[]::new;
	}

}