// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	interface X { int m(Iterable<String> arg); }
	interface Y { int m(Iterable<Integer> arg); }
	
	@FunctionalInterface
	interface Z extends X, Y {}
	  // Not functional: No method has a subsignature of all abstract methods
}