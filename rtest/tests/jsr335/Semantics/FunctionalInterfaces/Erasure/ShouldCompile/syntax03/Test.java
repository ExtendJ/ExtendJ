// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { Map execute(ArrayList a, int b, Map d); }
	interface Y { <S, T, A> Map<S, T> execute(ArrayList<S> b, int a, Map<Map<S,T>, A> c); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}