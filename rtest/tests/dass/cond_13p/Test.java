// Test definite assignment after logical OR and assignment.
// https://bitbucket.org/jastadd/jastaddj/issue/89/seemingly-incorrect-definite-assignedness
// .result=COMPILE_PASS
class Test {
	{
		int x;
		int y = 3;
		if (((boolean) (false || ((x=y)==2))) || true) ;
		int z = x; // x is definitely assigned here
	}
}
