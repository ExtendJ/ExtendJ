// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	interface X { void execute(ArrayList<Integer> a); }
	interface Y { void execute(ArrayList<Double> a); }
	
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}