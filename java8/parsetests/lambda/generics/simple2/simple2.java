
public class Test {
	public static void main(String[] args) {
		Object f = (Runnable<Double, Integer, String> r, Callable<Integer> c, int q) -> r.run() + c.call() + q;
    }
}
