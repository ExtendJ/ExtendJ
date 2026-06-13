// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { void execute(ArrayList a); }
	interface Y { <S> void execute(ArrayList<Integer> a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}