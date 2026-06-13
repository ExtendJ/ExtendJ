// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	interface A {
		String[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = String[]::new;
		
		boolean gotException = false;
		try {
			String[] s = a.m(-2);
		}
		catch(NegativeArraySizeException e) {
			gotException = true;
		}
		
		testTrue("ArrayReference", gotException);
	}
}
