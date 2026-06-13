// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	public interface A<T> {
		B<T> m(int a, String b);
	}
	
	public interface B<T> {
		C<T> m(T a, int b);
	}
	
	public interface C<T> {
		int m(T t, int a, String b);
	}
	
	public static A<String> someMethod() {
		return (a1, b1) -> (a2, b2) -> (a3, b3, c1) -> {
			return a1 + b1.length() + a2.length() + b2 + a3.length() + b3 + c1.length();
		};
	}
	
	public static void main(String[] arg) {
		A<String> a = someMethod();
		testTrue("Nested lambdas", a.m(3, "he").m("hej", 5).m("hejsan", 2, "ne") == 23);
	}
}
