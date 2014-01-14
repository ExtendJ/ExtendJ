import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        Object f = (Thread t) -> { t.start(); }; // Single declared-type parameter

    }
}
