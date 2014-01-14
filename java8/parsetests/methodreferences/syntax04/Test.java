import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = List::size;           // inferred class type args, or generic
	}
}