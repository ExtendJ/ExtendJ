import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = ArrayList::new;            // inferred class type args, or generic
	}
}