// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	interface Foo<T> { void m(T arg); }
	interface Bar<T> { void m(T arg); }
	
	@FunctionalInterface
	interface FooBar<X, Y> extends Foo<X>, Bar<Y> {}
	  // Compiler error: different signatures, same erasure
}