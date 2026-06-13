// .result=COMPILE_FAIL
import java.util.*;
import java.io.Serializable;

class Test {
	interface X { <S extends Integer & Serializable> void execute(ArrayList<S> d, S s); }
	interface Y { void execute(ArrayList b, int l); }
	
	@FunctionalInterface
	interface Exec extends X, Y { }
}