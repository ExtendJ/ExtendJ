// Tests float literal rounding to infinity.
// .result=COMPILE_FAIL
class Test {
	double _ = 0xE.eP+1024; // Bad float literal.
}
