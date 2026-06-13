// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {

	interface Foo<T, N extends Number> {
		void m(T arg);
		void m(N arg);
	}
	interface Bar extends Foo<String, Integer> {}
	
	@FunctionalInterface
	interface Baz extends Foo<Integer, Integer> {}
	// Baz is functional: same signature for m
}