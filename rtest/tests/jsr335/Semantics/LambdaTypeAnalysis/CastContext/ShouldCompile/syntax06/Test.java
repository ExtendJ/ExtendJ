// .result=COMPILE_PASS
public class Test {
	public interface TestInterface<T, S> {
		public T functMethod(T t, S s); 
	}
	
	public interface SubTestInterface<T, S> extends TestInterface<T, S> {
		
	}
	
	public static void main(String[] args) {
		TestInterface t = (SubTestInterface<Integer, Integer>)(Integer a, Integer b) -> a.intValue() + b.intValue();
		t = (SubTestInterface<Integer, Integer>)(a, b) -> a.intValue() + b.intValue();
    }
}