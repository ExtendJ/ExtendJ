// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	public interface A {
		int m();
	}
	
	public static void main(String[] arg) {
		A a = () -> 5;
		testTrue("Lambda", a.m() == 5);
		a = () -> {
			return 6;
		};
		testTrue("Lambda", a.m() == 6);
	}
}
