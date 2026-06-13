// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	interface X { long m(); }
	interface Y { int m(); }
	
	@FunctionalInterface
	interface Z extends X, Y {}
	  // Compiler error: no method is return type substitutable
}