// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	public interface A {
		int[] m(int i);
	}
	
	public interface B {
		int[] m(short i);
	}
	
	public static void main(String[] arg) {
		A a = int[]::new;
		B b = a::m;
		int[] arr = b.m((short)10);
		testTrue("ArrayReference", arr.length == 10);
		testTrue("ArrayReference", arr instanceof int[]);
		for(int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		for(int i = 0; i < arr.length; i++) {
			testTrue("ArrayReference", arr[i] == i);
		}		
	}
}
