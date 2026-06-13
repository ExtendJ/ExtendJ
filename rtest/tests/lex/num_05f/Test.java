// Test an illegal numeric literal.
// .result=COMPILE_FAIL
class Test {
	double _ = 0x.0e; // Missing exponent ('e' here is a digit in the fraction part).
}
