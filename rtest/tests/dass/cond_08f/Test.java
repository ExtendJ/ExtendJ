// Test definite assignment check with logical AND and assignment.
// .result=COMPILE_FAIL
class Test {
	{
		boolean v;
		if (false && (v=true)) ;
		boolean b = v;// Error: v not DA after `a` when false in `a && b`.
	}
}
