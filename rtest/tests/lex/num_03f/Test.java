// Test an illegal numeric literal.
// .result=COMPILE_FAIL
class Test {
	double _ = .0E; // missing exponent
}
