// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <A extends Integer, B extends A, C extends B> Double execute(int a); }
	interface Y { <A extends Integer, B extends A, C extends B> C execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}