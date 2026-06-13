//.result=COMPILE_FAIL

import java.util.*;
import java.io.Serializable;
public class Test {
	public static void main(String[] args) {
		HashMap<Integer, String> h = (Cloneable & Serializable) new HashMap<Integer, String>();
    }
}