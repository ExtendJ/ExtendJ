// .result=COMPILE_FAIL
import java.util.*;
import java.io.Serializable;

class Test {
	interface X { <S extends List<Integer> & Serializable> void execute(ArrayList<S> d, S s); }
	interface Y { void execute(ArrayList b, List<Integer> l); }
	
	@FunctionalInterface
	interface Exec extends X, Y { }
}