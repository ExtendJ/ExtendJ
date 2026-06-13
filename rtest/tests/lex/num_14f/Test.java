// Test too large integer literal
// .result=COMPILE_FAIL
class Test {
	int x = 040000000000; // This integer literal is too large.
}
