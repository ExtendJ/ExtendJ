// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public int m(int a);
	}
	
	public static void main(String[] args) {
		TestInterface t = a -> "hej";
    }
}
