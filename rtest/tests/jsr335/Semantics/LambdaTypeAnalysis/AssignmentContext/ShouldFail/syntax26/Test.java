// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public TestInterface m(int a);
	}
	
	public interface InnerTestInterface {
		public String m(String s);
	}
	
	public static void main(String[] args) {
		TestInterface t = a -> (String s) -> {
			return s.length();
		};
    }
}
