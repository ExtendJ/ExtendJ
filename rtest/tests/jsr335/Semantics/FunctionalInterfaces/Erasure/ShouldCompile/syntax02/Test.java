// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {
	interface X { List execute(ArrayList a); }
	interface Y { <S> List<S> execute(ArrayList<S> a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}