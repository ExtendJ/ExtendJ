// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {
	
	@FunctionalInterface
	interface Foo {
		int m();
		Object clone();
	}
	// Not functional; method Object.clone is not public
}