// .result=COMPILE_FAIL
import java.util.*;


public class Test {
	
	interface A {
		int[][] m(int i);
		void m2();
	}
	
	public static void main(String[] arg) {
		A a = int[][]::new;
	}

}