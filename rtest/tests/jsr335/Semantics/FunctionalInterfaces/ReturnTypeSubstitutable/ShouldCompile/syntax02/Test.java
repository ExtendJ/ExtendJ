// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { double execute(int a); }
	interface Y { double execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}