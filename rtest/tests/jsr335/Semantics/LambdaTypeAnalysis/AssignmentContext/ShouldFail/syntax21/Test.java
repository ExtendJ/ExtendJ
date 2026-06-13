// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface<T> {
		public InnerTestInterface<T> functMethod(int a); 
	}
	
	public interface InnerTestInterface<T> {
		public InnerInnerTestInterface<Integer, T> functMethod(T t);
	}
	
	public interface InnerInnerTestInterface<S, T> {
		public T functMethod(S s); 
	}
	
	public static void main(String[] args) {
		TestInterface<String> t = (int a) -> {
			return (String s) -> {
				return (String i) -> "Hejsan";
			};
		};
	}
}
