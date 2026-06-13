// .result=COMPILE_FAIL
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	public TestInterface t = a -> a + 5;
	
	public interface TestInterface {
		 public int m(String a);
	}
	
	public static void main(String[] args) {
		
    }
}
