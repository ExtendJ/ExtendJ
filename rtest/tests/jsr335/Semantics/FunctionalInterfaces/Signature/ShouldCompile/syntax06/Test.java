// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;


class Test {
	interface X { <S, T> int execute(ArrayList<T> a); }
	interface Y { <T, S> int execute(ArrayList<S> a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}