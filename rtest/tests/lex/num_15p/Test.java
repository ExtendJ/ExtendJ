// Test large octal literal.
// .result=COMPILE_PASS
class Test {
	int x = 020000000000; // Octal literal within int bounds.
}
