// .result=COMPILE_FAIL
import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		boolean b;
		//Must include parantheses, should give syntactic error
		Object f = b ? new ArrayList<Integer>() : new List<Integer> String::iterator;
	}
}