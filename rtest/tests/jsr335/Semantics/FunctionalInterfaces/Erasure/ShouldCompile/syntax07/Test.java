// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { <S> void execute(ArrayList<S> d, S s); }
	interface Y { void execute(ArrayList b, Object o); }
	
	@FunctionalInterface
	interface Exec extends X, Y { }
}