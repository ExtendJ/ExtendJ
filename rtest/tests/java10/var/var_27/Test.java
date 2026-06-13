//.result=COMPILE_PASS

import java.util.HashMap;
import java.io.Serializable;
public class Test {
	public static void main(String[] args) {
        var map = (Cloneable & Serializable) new HashMap<Integer, String>();
		test(map);
    }
	public static void test(Serializable ser) {

	}
}