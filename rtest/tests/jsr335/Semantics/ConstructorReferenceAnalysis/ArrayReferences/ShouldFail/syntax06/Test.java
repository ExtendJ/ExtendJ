// .result=COMPILE_FAIL
import java.util.*;


public class Test {
	
	interface A {
		int[][] m(long i);
	}
	
	public static void main(String[] arg) {
		A a = int[][]::new;
	}

}