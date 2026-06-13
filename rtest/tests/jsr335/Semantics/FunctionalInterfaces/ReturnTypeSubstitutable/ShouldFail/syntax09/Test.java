// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <A extends Integer> A execute(int a); }
	interface Y { <B extends Integer> ArrayList<? extends Number> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}