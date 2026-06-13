// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public void m(int a);
	}
	
	public static void main(String[] args) {
		TestInterface t = a -> a + 4;
    }
}
