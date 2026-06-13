// .result=COMPILE_PASS
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	public interface A<T> {
		 public B<T> m(T a);
	}
	
	public interface B<T> {
		 public C<String> m(T a, double b);
	}
	
	public interface C<T> {
		 public D<T> m(T a);
	}
	
	public interface D<T> {
		 public E<List<T>> m(T a);
	}
	
	public interface E<T> {
		 public F<String> m(List<T> a);
	}
	
	public interface F<T> {
		 public int m(T a);
	}
	
	public static void main(String[] args) {
		A<StringBuilder> aInterface = a1 -> (a2, b1) -> a3 -> a4 -> a5-> a6 -> {
			return a2.toString().length() + a3.length() + a5.get(0).get(0).length() + a6.length();
		};
    }
}
