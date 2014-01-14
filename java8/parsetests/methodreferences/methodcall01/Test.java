import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		//Conflict with: methodCall(Runnable < Integer, Double, String, Double);
		Object f = methodCall(Runnable<Integer, Double, String, Double>::run);
	}
}