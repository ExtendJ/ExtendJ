// .result=COMPILE_PASS
import java.util.*;



class Test {
	
	interface X { <S> void execute(ArrayList<?> a, S s); }
	interface Y { <T> void execute(ArrayList<?> b, T t); }
	
	@FunctionalInterface
	interface Exec extends X, Y {

	}
}