import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        Object f = (x, final y) -> x+y; // Illegal: can't modify inferred-type parameters

    }
}
