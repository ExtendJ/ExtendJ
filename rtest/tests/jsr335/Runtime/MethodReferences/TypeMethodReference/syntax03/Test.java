// .classpath: @RUNTIME_CLASSES@
import static java.lang.String.valueOf;
import static runtime.Test.*;

import java.util.*;

public class Test {
	
	interface A<T> {
		int m(T t);
	}
	
	public static <T extends List<Integer>> int method(T t) {
		int sum = 0;
		for(Integer i : t)
			sum += i;
		return sum;
	}
	
	public static void main(String[] arg) {
		A<ArrayList<Integer>> a = Test::<ArrayList<Integer>>method;
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(3);
		testTrue("MethodRef", a.m(list) == 5);
	}
}
