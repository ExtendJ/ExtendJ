// Test overriding final method in enum superclass (java.lang.Enum)
// .result=COMPILE_FAIL
enum Test {
	;
	public int compareTo(java.lang.Enum other) { // conflicts with supertype decl due to erasure
		return 1;
	}
}
