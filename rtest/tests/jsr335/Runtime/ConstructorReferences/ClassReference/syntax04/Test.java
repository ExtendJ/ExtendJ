// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {

	public interface A {
		List<String> m();
	}
	
	public static void main(String[] arg) {
		// Tests type variable inferrence (uses diamond internally)
		A a = LinkedList::new;
		LinkedList<String> list = (LinkedList<String>)a.m();
		list.add("yes");
		testTrue("ConstructorReference", list.size() == 1);
		testTrue("ConstructorReference", list.get(0).equals("yes"));
	}
}
