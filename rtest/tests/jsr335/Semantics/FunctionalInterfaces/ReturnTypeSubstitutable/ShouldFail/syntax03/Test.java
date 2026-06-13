// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { int execute(int a); }
	interface Y { long execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}