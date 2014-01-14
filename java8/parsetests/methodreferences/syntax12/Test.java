import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
		Object f = Arrays::sort;         // type args (if any) inferred from context
	}
}