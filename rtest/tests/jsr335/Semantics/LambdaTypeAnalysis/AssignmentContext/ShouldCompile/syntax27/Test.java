// .result=COMPILE_PASS
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	int a = 6;
	public TestInterface t = () -> {
		if(a > 4)
			return 2;
		else
			return 7;
	};
	
	public interface TestInterface {
		 public int m();
	}

	
	public static void main(String[] args) {

    }
}
