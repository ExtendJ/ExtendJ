// .result=COMPILE_FAIL
interface Test {
	// multiple declaration with same signature is not permitted within the
	// same interface
	Float m(float f111);
	Float m(float c9124);
}
