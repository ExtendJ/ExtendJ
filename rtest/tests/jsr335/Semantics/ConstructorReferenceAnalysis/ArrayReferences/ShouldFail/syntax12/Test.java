// .result=COMPILE_FAIL
import java.util.*;


public class Test {
	
	interface A {
		String[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = (((A)String[]::new)::m)::m;
	}
}