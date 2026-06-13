// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	int i = 0;
	
	public interface A {
		void m(int i);
	}
	public interface B {
		int m();
	}
	
	public void setI(int i) {
		this.i = i;
	}
	
	public int getI() {
		return this.i;
	}
	
	public static void main(String[] arg) {
		Test[] t = {new Test()};
		A a = t[0]::setI;
		B b = t[0]::getI;
		t = null;
		a.m(4);
		testTrue("MethodRef", b.m() == 4);
		a.m(2);
		testTrue("MethodRef", b.m() == 2);
	}
}
