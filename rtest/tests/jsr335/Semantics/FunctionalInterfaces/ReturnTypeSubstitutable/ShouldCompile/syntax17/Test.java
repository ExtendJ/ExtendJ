// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X<I> { <B extends I, A extends B, C extends A, D extends C> D execute(int a); }
	interface Y<I> { <A extends I, B extends A, D extends B, C extends D> C execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y<Integer>, X<Integer> { }
}