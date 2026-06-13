// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {
	//Changed Action<T> and Action<S> to ArrayLists to avoid compiler error
	@FunctionalInterface
	interface X { <T> T execute(ArrayList<T> a); }
	
	@FunctionalInterface
	interface Y { <S> S execute(ArrayList<S> a); }
	
	@FunctionalInterface
	interface Exec extends X, Y {}
	// Functional: signatures are "the same"
}