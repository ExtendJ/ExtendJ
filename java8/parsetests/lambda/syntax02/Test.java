import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Callable<Integer> f = (x -> x+1);
	}
}