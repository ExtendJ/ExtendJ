// Not overriding final Enum method
// .result=COMPILE_PASS
enum Test {
	;
	public int compareTo(int o) {// does not override java.lang.Enum.compareTo
		return o;
	}
}
