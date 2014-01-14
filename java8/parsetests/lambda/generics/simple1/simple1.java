
public class Test {
	public static void main(String[] args) {
		Object f = (int x, Callable<Integer, String> c) -> { return c.call().add(x); };
    }
}
