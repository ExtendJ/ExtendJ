// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { ArrayList<Integer> execute(int a); }
	interface Y { ArrayList<Integer> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}