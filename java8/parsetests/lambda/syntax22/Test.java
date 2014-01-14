import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        Object f = t -> { t.start(); }; // Single inferred-type parameter

    }
}
