// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	interface A {
		int m();
	}
	
	public static int method() {
		return 5;
	}
	
	public static void main(String[] arg) {
		A a = Test::method;
		testTrue("MethodRef", a.m() == 5);
	}
}
