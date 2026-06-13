// .result=COMPILE_FAIL
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	public interface TestInterface {
		 public int m(String a);
	}
	
	public static void main(String[] args) {
		TestInterface[][] t = {a -> 4, a -> 6, a -> 3};
    }
}
