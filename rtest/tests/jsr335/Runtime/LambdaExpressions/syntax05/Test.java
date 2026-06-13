// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	
	public interface A {
		ArrayList<Integer> m(int start, int end);
	}
	
	public static void main(String[] arg) {
		A a = (start, end) -> {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int i = start; i < end; i++) {
				list.add(i);
			}
			return list;
		};
		
		ArrayList<Integer> list = a.m(2, 17);
		testTrue("Lambda", list.size() == 15);
		int index = 0;
		for(int i = 2; i < 17; i++) {
			testTrue("Lambda", list.get(index) == i);
			index++;
		}
	}
}
