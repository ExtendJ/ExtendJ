// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	interface X { <T> void execute(ArrayList<T> a); }
	interface Y { <S> void execute(ArrayList a); }
	
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}