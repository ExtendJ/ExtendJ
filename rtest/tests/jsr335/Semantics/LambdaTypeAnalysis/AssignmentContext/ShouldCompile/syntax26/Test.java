// .result=COMPILE_PASS
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	public interface TestInterface {
		 public int m();
	}
	
	public static void main(String[] args) {
		int a = 4;
		int b = 6;
		TestInterface[] t = {() -> 5, () -> a + b, () -> { return a + 5; }};
    }
}
