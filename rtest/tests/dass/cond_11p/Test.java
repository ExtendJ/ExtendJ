// Test definite assignment check with logical OR and assignment.
// .result=COMPILE_PASS
class Test {
	{
		int u;
		int v;
		if ((true && ((u=4) == 3)) || ((v=u) == 2)) ;
	}
}
