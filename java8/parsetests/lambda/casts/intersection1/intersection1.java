
public class Test {
	public static void main(String[] args) {
		Object f = (Callable<Integer> & Runnable<? super T> & Clonable) x -> x + y -> y + z -> 6;
    }
}
