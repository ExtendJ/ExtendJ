// .result=COMPILE_FAIL
import java.util.*;
import java.io.Serializable;

class Test {
	interface X { <B, A, S extends Map<?, List<B>>> void execute(int a); }
	interface Y { <A, B, S extends Map<?, List<B>>> void execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}