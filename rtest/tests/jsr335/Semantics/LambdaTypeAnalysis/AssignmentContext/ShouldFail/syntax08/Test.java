// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public int m(int a, int b);
	}
	
	public static void main(String[] args) {
		TestInterface t = a -> 5;
    }
}
