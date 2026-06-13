// .result=COMPILE_FAIL

public class Test {
	
	public interface A<T> {
		public B<T> m(T t);
	}
	
	public interface B<T> {
		public C<T> m(T t);
	}
	
	public interface C<T> {
		public int m(T t);
	}
	
	public static void main(String[] args) {
		A t = (A<String>)a -> b -> c -> a + b + c;
    }
}
