// The method compareTo in Test does not override compareTo in Comparable<T>,
// with T=Test, but has the same erased signature.
// .result=COMPILE_FAIL
class Test extends C<Test> {
	public int compareTo(Object o) {
		return 0;
	}
}

class C<T extends C> implements Comparable<T> {
	public int compareTo(T o) {
		return 1;
	}
}
