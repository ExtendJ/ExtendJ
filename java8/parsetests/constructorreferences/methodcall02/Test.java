import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		//Conflict check that >>> works with constructor references
		Object f = methodCall(4, Runnable<Integer, ArrayList<Callable<String>>>::new, a);
	}
}