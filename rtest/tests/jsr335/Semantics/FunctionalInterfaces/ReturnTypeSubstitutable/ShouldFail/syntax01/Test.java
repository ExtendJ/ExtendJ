// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <A, T, S extends List<A>> List<T> execute(ArrayList<S>[][] a); }
	interface Y { <A, S, T extends List<A>> T execute(ArrayList<T>[][] a); }
	
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}