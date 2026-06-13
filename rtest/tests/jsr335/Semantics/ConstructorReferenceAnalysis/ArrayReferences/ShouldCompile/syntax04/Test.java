// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A {
		String[][] m(short i);
	}
	
	public static void main(String[] arg) {
		A a = String[][]::new;
	}
}