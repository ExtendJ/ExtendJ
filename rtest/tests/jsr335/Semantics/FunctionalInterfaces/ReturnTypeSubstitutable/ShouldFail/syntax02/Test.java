// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { <S extends Integer> ArrayList<Integer> execute(int a); }
	interface Y { <T extends Integer> ArrayList<T> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}