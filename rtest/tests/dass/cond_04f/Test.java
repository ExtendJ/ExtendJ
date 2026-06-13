// Test definite assignment check with logical AND and assignment.
// .result=COMPILE_FAIL
class Test {
	{
		final int v;
		if (((v=2) == 3) && ((v=3) == 2)) ;
		// Error: v is not definitely unassigned before `b` in `a && b` here.
	}
}
