// .result=COMPILE_FAIL
import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = someVar[3]::new; 
	}
}