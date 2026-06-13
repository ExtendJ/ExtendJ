// .result=COMPILE_FAIL
class Test implements Comparable<Test> {
	// error: local compareTo does not match interface method
	public int compareTo(Object o) {
		return 0;
	}
}
