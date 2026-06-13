// Tests definite assignment in assert statement.
// The variable j is definitely assigned after the first part of the assert statement.
// http://svn.cs.lth.se/trac/jastadd-trac/ticket/67
// .result=COMPILE_PASS
class Test {
	void m() {
		int i = 0, j;
		assert i == (j=0) : j;
	}
}
