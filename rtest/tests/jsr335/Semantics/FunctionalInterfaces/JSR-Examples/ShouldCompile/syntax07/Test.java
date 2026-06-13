// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {

	@FunctionalInterface
	interface Executor { 
		//Changed Action<T> to ArrayList<T> to avoid compiler error
		<T> T execute(ArrayList<T> a); 
	}
	// Functional
}