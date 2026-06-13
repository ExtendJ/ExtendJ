// .result=COMPILE_FAIL
import java.util.*;
import java.io.Serializable;

class Test {
	interface X { <A extends ArrayList<A>> void execute(int a); }
	interface Y { <B extends ArrayList<Integer>> void execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}