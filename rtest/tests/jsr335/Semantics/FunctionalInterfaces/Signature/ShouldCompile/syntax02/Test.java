// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;


class Test {
	interface X { <S, T> int execute(ArrayList<? extends ArrayList<S>> a); }
	interface Y { <T, S> int execute(ArrayList<? extends ArrayList<T>> a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}