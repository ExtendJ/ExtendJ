// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public String m(int a, String b);
	}
	
	public static void main(String[] args) {
		TestInterface t = (a, b) -> {
			int c = a + b;
			return b;
		};
    }
}
