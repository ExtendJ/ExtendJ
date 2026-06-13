// .result=COMPILE_PASS
import java.util.*;


class Test {
	interface X { <S, T> int execute(ArrayList<? extends Map<ArrayList<S>, ArrayList<T>>> a); }
	interface Y { <T, S> int execute(ArrayList<? extends Map<ArrayList<T>, ArrayList<S>>> a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}