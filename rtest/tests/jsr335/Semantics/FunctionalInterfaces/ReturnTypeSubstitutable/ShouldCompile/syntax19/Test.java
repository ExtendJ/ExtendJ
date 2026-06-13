// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { <A, B extends A, C extends B> A execute(int a); }
	interface Y { <A, B extends A, C extends B> C execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}