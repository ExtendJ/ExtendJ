// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { <A, B extends ArrayList<A>> B execute(int a); }
	interface Y { <B, A extends ArrayList<B>> ArrayList<B> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}