import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = Bar<String>::<Integer>new; // generic class, generic constructor
	}
}