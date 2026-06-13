// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	public interface A {
		int[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = int[]::new;
		int[] arr = a.m(10);
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
