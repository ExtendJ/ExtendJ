// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A<T extends List<Integer>> {
		public B[] m(T t);
	}
	
	public interface B {
		public String[] m();
	}
	
	public static <T extends List<Integer>> A<T> getA() {
		return t -> {
			B[] bs = new B[t.size()];
			int index = 0;
			for(Integer i : t) {
				bs[index] = () -> new String[i];
				index++;
			}
			return bs;
		};
	}
	
	public static void main(String[] arg) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++) {
			list.add(i);
		}
		A<ArrayList<Integer>> a = getA();
		B[] bs = a.m(list);
		testTrue("Lambda", bs.length == 10);
		for(int i = 0; i < 10; i++) {
			testTrue("Lambda", bs[i].m().length == i);
		}
	}
}
