// Test definite assignment check with logical AND and assignment.
// .result=COMPILE_PASS
class Test {
	{
		int u;
		int v;
		if ((true && ((u=3) == 2)) && ((v=u)==2)) ;
	}
}
