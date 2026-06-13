// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { ArrayList<Integer> execute(int a); }
	interface Y { ArrayList<Double> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}