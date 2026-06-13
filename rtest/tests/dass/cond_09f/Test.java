// Test definite assignment check with logical OR and assignment.
// .result=COMPILE_FAIL
class Test {
	{
		boolean v;
		if (true || (v=true)) ;
		boolean b = v;
		// Error: v is not definitely assigned after `a` when true in `a || b`.
	}
}
