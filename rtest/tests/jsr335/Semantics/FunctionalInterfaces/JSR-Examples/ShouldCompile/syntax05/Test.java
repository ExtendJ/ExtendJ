// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {

	@FunctionalInterface
	interface X { Iterable m(Iterable<String> arg); }
	
	@FunctionalInterface
	interface Y { Iterable<String> m(Iterable arg); }
	
	@FunctionalInterface
	interface Z extends X, Y {}
	  // Functional: Y.m is a subsignature & return-type-substitutable
}