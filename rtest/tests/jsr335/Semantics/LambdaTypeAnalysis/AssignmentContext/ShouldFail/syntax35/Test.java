// .result=COMPILE_FAIL
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	public interface A<T extends List<Integer>> {
		public B m(T t);
	}
	
	public interface B {
		public int[][] m(int len);
	}
	
	public static <T extends List<Integer>> A<T> getA() {
		return (List<Integer> a) -> null;
	}
}