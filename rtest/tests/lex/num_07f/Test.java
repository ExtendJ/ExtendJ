// Test an illegal numeric literal.
// .result=COMPILE_FAIL
class Test {
	double _ = 0xp1; // Missing digits.
}
