// Test definite assignment check with logical OR and assignment.
// https://bitbucket.org/jastadd/jastaddj/issue/88/incorrect-definite-unassignment-check
// .result=COMPILE_FAIL
class Test {
	{
		final int v;
		if (((v=3) == 3) || ((v=2) == 2)) ;
		// Error: v is not definitely unassigned before `b` in `a || b` here.
	}
}
