// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

class Test {

	@FunctionalInterface
	interface Comparator<T> {
		boolean equals(Object obj);
		int compare(T o1, T o2);
	}
	// Functional; Comparator has one abstract non-Object method
}