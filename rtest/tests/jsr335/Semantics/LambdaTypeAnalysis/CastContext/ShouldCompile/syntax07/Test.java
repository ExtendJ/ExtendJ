// .result=COMPILE_PASS
public class Test {
	public interface TestInterface<T, S> {
		public T functMethod(T t, S s); 
	}
	
	public interface InnerTestInterface<T, S>  {
		public TestInterface<T,S> functMethod(T t);
	}
	
	public static void main(String[] args) {
		InnerTestInterface<String, Integer> t = (String a) -> {
			if(args.length > 3)
				return (TestInterface<String, Integer>)(b, c) -> b;
			else
				return (b, c) -> b;
		};
    }
}