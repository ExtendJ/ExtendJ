// .result=COMPILE_FAIL

public class Test {
	
	public interface TestInterface<T> {
		public String m(T t);
	}
	
	public static void main(String[] args) {
		TestInterface t = (TestInterface<Integer>)a -> a;
    }
}
