// .result=COMPILE_PASS

public class Test {
	public interface TestInterface<T> {
		public T functMethod(Integer a); 
	}
	
	public interface SubTestInterface<T> extends TestInterface<T> { }
	
	public static void main(String[] args) {
		TestInterface t = (SubTestInterface<Integer>)a -> a;
    }
}