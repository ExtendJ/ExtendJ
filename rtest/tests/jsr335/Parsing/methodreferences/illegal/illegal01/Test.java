// .result=COMPILE_FAIL
import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		//Not allowed to include parameters, should give syntactic error
		Object f = String::charAt(a, b);
	}
}