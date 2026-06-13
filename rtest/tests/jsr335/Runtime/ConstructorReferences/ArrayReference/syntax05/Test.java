// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	interface A {
		String[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = String[]::new;
		
		testTrue("ArrayReference", a.m(2) != a.m(2));
	}
}
