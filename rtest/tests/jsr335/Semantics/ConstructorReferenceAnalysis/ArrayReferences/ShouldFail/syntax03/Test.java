// .result=COMPILE_FAIL
import java.util.ArrayList;


public class Test {
	
	interface A {
		String[][] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = String[][][]::new;
	}

}