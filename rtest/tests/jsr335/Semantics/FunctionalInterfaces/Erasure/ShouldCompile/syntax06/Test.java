// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface D { <S,T,A> void execute(ArrayList<Map<S, Map<T,A>>> b, int[] a); }
	interface E { <A,B,C> void execute(ArrayList<Map<A, Map<B,C>>> b, int[] a); }
	interface F {         void execute(ArrayList b, int[] a); }
	
	@FunctionalInterface
	interface Exec extends D, E, F { }
}