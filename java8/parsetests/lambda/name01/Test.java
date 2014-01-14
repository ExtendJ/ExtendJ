import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        int x = 0;
		Object f = (Object x) -> { return x.toString(); };
    }
}
