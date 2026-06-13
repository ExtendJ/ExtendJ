// .result=COMPILE_OUTPUT
// .options=XstructuredPrint

public class Test {
	public static void main(String[] args) {
		Object f = (Callable<? extends Comparable<? super T<Integer>>> c) -> c.run();
    }
}
