// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <A extends Map<ArrayList<B>, ? extends Integer>, B extends ArrayList<Double>, C extends ArrayList<A>> C execute(int a); }
	interface Y { <B extends Map<ArrayList<A>, ? extends Integer>, A extends ArrayList<Double>, D extends ArrayList<B>> ArrayList<? extends Map<? extends ArrayList<ArrayList<Double>>, ? extends Integer>> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}