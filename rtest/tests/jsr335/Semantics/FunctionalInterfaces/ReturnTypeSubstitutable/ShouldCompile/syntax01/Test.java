// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { int execute(int a); }
	interface Y { int execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}