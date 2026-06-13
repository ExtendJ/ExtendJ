// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	//Changed Action<T> and Action<S> to ArrayLists to avoid compiler error
	interface X { <T> T execute(ArrayList<T> a); }
	interface Y { <S,T> S execute(ArrayList<S> a); }
	
	@FunctionalInterface
	interface Exec extends X, Y {}
	// Compiler error: different signatures, same erasure
}