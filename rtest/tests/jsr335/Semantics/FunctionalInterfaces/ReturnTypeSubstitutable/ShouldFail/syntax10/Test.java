// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <B, A extends ArrayList<? super B>> A execute(int a); }
	interface Y { <A, B extends ArrayList<? extends A>> B execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}