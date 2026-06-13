// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	public interface A {
		String m(int a, int b);
	}
	
	public static void main(String[] arg) {
		A lambda = (int a, int b) -> "" + a + b;
		testEquals("Lambda", lambda.m(4, 9), "49");
	}
}
