// Test definite assignment check with logical AND and assignment.
// .result=COMPILE_FAIL
class Test {
	{
		final int v;
		if ((false && ((v=2) == 3)) && ((v=3) == 2)) ;
		// Even though `a` in `a && b` can obviously not be true
		// v is not definitely unassigned before `b` here.
	}
}
