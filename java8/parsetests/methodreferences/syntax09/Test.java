import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = (test ? list.map(String::length) : Collections.emptyList())::iterator;
	}
}