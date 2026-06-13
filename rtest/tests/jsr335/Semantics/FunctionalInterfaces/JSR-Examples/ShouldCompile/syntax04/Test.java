// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {

	@FunctionalInterface
	interface X { int m(Iterable<String> arg); }
	
	@FunctionalInterface
	interface Y { int m(Iterable<String> arg); }
	
	@FunctionalInterface
	interface Z extends X, Y {}
	  // Functional: two methods, but they have the same signature
}