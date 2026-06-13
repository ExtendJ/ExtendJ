// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { <A, B extends A> A execute(int a); }
	interface Y { <C, B extends C> B execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}