import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		//one > is missing, should give syntactic error
		Object f = methodCall(a, Runnable<ArrayList<Integer>, Callable<Integer>::run, 3);
	}
}