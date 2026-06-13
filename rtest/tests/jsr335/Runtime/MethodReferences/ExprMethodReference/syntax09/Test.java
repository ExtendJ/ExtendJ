// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A {
		int m(ArrayList<Integer> t);
	}
	
	public <T extends List<Integer>> int method(T list) {
		int sum = 0;
		for(Integer i : list) {
			sum += i;
		}
		return sum;
	}
	
	public static void main(String[] arg) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(6);
		list.add(4);
		
		Test t = new Test();
		A a = t::method;
		testTrue("MethodRef", a.m(list) == 12);
	}
}



