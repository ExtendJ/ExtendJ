// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	interface A {
		List<Integer>[][] m(int i);
	}
	interface B {
		ArrayList<Integer>[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = List[][]::new;
		B b = ArrayList[]::new;
		List<Integer>[][] arr = a.m(10);
		testTrue("ArrayReference", arr.length == 10);
		testTrue("ArrayReference", arr instanceof List[][]);
		
		for(int i = 0; i < arr.length; i++) {
			arr[i] = b.m(i);
		}
		
		for(int i = 0; i < arr.length; i++) {
			testTrue("ArrayReference", arr[i].length == i);
			testTrue("ArrayReference", arr[i] instanceof ArrayList[]);
		}
		
		boolean gotException = false;
		try {
			arr[0][0] = new ArrayList<Integer>();
		} catch(IndexOutOfBoundsException e) {
			gotException = true;
		}
		testTrue("ArrayReference", gotException);
		
		arr[1][0] = new ArrayList<Integer>();
	}
}
