// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	interface Foo<T, N extends Number> {
		void m(T arg);
		void m(N arg);
	}
	
	@FunctionalInterface
	interface Bar extends Foo<String, Integer> {}
	// Bar is _not_ functional: different signatures for m
}