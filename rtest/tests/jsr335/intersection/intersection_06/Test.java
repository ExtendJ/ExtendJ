//.result=COMPILE_FAIL

import java.util.*;
import java.io.Serializable;
public class Test {
	public static void main(String[] args) {
		String x = (Cloneable & Serializable) "x";
    }
}