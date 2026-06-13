// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <A, B> ArrayList<A> execute(int a); }
	interface Y { <B, A> ArrayList<A> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}