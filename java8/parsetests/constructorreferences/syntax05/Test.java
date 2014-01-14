import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = Outer.Inner::new;          // inner class constructor
	}
}