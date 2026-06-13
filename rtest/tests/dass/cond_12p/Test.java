// Test definite assignment after logical OR ans assignment.
// .result=COMPILE_PASS
class Test {
	{
		int x;
		int y = 3;
		if (((false || ((x=y)==2)) == true) || true) ;
		int z = x; // x is definitely assigned here
	}
}
