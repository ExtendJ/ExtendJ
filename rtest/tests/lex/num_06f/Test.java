// Test an illegal numeric literal.
// .result=COMPILE_FAIL
class Test {
	double _ = .E0d; // Missing digits.
}
