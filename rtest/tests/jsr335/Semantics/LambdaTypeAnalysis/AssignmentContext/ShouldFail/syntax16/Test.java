// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public String m(int a);
	}
	
	public static void main(String[] args) {
		int b = 5;
		TestInterface t = a -> {
			return b;
		};
    }
}
