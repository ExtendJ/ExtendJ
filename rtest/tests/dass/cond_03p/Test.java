// Test definite assignment check with logical AND and assignment.
// .result=COMPILE_PASS
class Test {
	{
		{
			final int v;
			if (false && ((v=3) == 2)) ;
		}

		{
			final int v;
			if (true && ((v=3) == 2)) ;
		}

		{
			final int v;
			if (((v=3) == 2) && true) ;
		}

		{
			final int v;
			if (((v=3) == 2) && false) ;
		}
	}
}
