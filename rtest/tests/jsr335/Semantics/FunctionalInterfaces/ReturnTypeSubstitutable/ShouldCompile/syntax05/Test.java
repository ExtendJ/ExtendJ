// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { <S, T> ArrayList<S> execute(int a); }
	interface Y { <T, S> ArrayList<T> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}