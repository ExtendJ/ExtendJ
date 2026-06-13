// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface<T> {
		 public int m(T t);
	}
	
	public static void main(String[] args) {
		TestInterface<Double> t = (Integer a) -> 5;
    }
}
