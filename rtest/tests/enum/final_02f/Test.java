// Test overriding final method in enum superclass (java.lang.Enum)
// .result=COMPILE_FAIL
enum Test {
	;
	protected void finalize() {
	}
}
