// .result=COMPILE_FAIL
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	
	public interface A {
		int m();
	}
	
	public static void main(String[] arg) {
		A a = (A)(arg.length==4 ? ()->3 : ()->5);
	}
}