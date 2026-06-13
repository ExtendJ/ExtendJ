// Test an illegal numeric literal.
// .result=COMPILE_FAIL
class Test {
	double _ = 0x.ep-; // Missing exponent digits after 'p-'.
	// This will not scan correctly because the scanner macro requires digits after the minus.
}
