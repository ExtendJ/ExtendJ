// Enum declarations can not have an extends clause
// .result=COMPILE_FAIL
enum Test extends java.lang.Enum<Test> {
	;
	String name() {// error - name is final in java.lang.Enum
		return "";
	}
}
