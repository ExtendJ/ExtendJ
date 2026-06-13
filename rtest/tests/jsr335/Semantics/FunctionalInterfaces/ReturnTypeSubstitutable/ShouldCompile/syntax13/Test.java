// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X<A> { ArrayList<A> execute(int a); }
	interface Y<B> { ArrayList<B> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y<Integer>, X<Integer> { }
}