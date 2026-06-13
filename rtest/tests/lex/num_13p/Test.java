// Test large negative integer literal.
// .result=COMPILE_PASS
class Test {
	int x = -2147483648; // Decimal literal within int bounds.
}
