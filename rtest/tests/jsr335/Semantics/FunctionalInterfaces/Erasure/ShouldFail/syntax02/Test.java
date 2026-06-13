// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	interface X { <S, T> void execute(Map<ArrayList<Integer>, S> a); }
	interface Y { <T, S> void execute(Map<T, S> a); }
	interface Z { <T, S> void execute(Map<S, T> a); }
	
	
	@FunctionalInterface
	interface Exec extends Y, X, Z { }
}