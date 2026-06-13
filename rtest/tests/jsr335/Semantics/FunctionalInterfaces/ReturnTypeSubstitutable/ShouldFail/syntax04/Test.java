// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X { void execute(int a); }
	interface Y { int execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}