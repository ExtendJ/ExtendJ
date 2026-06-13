// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface<T> {
		 public String m(T a);
	}
	
	public static void main(String[] args) {
		TestInterface<Integer> t = a -> a;
    }
}
