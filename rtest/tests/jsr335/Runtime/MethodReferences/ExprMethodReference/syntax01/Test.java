// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	int i = 0;
	
	public interface A {
		int m();
	}
	
	public int method() {
		return i;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t::method;
		t.i = 5;
		testTrue("MethodRef", a.m() == 5);
		t.i = 7;
		t = null;
		testTrue("MethodRef", a.m() == 7);
	}
}
