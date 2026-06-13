// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X { ArrayList[] execute(ArrayList[][] d); }
	interface Y { <S> ArrayList<S>[] execute(ArrayList<S>[][] b); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}