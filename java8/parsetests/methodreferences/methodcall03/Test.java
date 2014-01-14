import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		//Testing if it can handle the >> operator to match a methodreference
		Object f = methodCall(a, Runnable<ArrayList<Integer>, Callable<Integer>>::run, 3);
	}
}