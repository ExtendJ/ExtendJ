// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { void execute(ArrayList d, int[] a); }
	interface Y { void execute(ArrayList<Integer> b, int[] a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}