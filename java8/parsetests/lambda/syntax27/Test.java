import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        Object f = (x, int y) -> x+y; // Illegal: can't mix inferred and declared types
    }
}
