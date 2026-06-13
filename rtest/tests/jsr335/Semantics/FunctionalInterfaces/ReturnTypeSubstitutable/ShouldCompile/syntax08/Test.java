// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { <S extends Integer> ArrayList<? extends Integer> execute(int a); }
	interface Y { <T extends Integer> ArrayList<T> execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}