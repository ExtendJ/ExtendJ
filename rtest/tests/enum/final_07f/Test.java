// Test overriding final method in enum superclass (java.lang.Enum)
// https://bitbucket.org/jastadd/jastaddj/issue/100/able-to-override-compareto-in-enum
// .result=COMPILE_FAIL
enum Test {
	;
	public int compareTo(Object other) { // conflicts with supertype decl due to erasure
		return 1;
	}
}
