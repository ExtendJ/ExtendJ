// Test too large integer literal
// https://bitbucket.org/jastadd/jastaddj/issue/101/illegal-decimal-integer-literal
// .result=COMPILE_FAIL
class Test {
	int x = 2147483648;// integer literal too large
}
