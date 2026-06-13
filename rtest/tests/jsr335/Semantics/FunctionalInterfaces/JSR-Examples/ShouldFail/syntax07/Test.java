// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	@FunctionalInterface
	interface Foo<T, N extends Number> {
		void m(T arg);
		void m(N arg);
	}
	// Foo is _not_ functional: different signatures for m

}