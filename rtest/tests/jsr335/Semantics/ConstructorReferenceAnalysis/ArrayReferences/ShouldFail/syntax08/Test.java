// .result=COMPILE_FAIL
import java.util.*;


public class Test {
	
	interface A {
		int[][] m(List<Integer> i);
	}
	
	public static void main(String[] arg) {
		A a = int[][]::new;
	}

}