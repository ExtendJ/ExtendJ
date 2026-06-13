//.result=COMPILE_PASS

import java.util.HashMap;
import java.io.Serializable;
public class Test {
	public static void main(String[] args) {
		test((Cloneable & Serializable) new HashMap<Integer, String>());
    }

	public static void test(Serializable ser) {

	}
}