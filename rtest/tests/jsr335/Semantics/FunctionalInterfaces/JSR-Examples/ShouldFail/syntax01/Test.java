// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;

class Test {

	@FunctionalInterface
	interface Foo { boolean equals(Object obj); }
	// Not functional; equals is already an implicit member
}