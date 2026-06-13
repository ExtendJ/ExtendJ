// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	public interface A {
		ArrayList<Number> m();
	}
	public interface B {
		ArrayList<Number> m(Collection<? extends Number> c);
	}
	
	public static void main(String[] arg) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(35);
		A a = ArrayList<Number>::new;
		B b = ArrayList<Number>::new;
		ArrayList<Number> aList1 = a.m();
		ArrayList<Number> aList2 = b.m(list);
		testTrue("ConstructorReference", aList1.size() == 0);
		testTrue("ConstructorReference", aList2.size() == 4);
		testTrue("ConstructorReference", aList2.get(0).intValue() == 10);
		testTrue("ConstructorReference", aList2.get(1).intValue() == 20);
		testTrue("ConstructorReference", aList2.get(2).intValue() == 30);
		testTrue("ConstructorReference", aList2.get(3).intValue() == 35);
	}
}
