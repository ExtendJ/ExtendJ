// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {
	interface Foo { boolean equals(Object obj); }

	@FunctionalInterface
	interface Bar extends Foo { int compare(String o1, String o2); }
	// Functional; Bar has one abstract non-Object method
}