// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { <A, B extends ArrayList<? extends A>> B execute(int a); }
	interface Y { <B, A extends ArrayList<? extends B>> ArrayList<? extends B> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}