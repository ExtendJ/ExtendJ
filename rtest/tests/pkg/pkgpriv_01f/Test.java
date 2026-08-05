// Can not access package private class from another package.
// Tests the error message generated for this type of error.
// See issue 93.
// .result=COMPILE_FAIL
class Test {
	p1.A a;
}
