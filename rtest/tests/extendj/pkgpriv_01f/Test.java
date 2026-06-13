// Can not access package private class from another package.
// Tests the error message generated for this type of error.
// https://bitbucket.org/jastadd/jastaddj/issue/93/asttypeaccess-used-in-type-access-error
// .result=COMPILE_ERR_OUTPUT
class Test {
	p1.A a;
}
