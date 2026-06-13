// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	public interface A {
		int m();
	}
	
	static int outerInt = 3;
	
	public static void main(String[] arg) {
		A a = () -> 5 + outerInt;
		testTrue("Lambda", a.m() == 8);
		outerInt++;
		testTrue("Lambda", a.m() == 9);
	}
}
