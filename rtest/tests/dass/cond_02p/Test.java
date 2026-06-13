// Test definite assignment check with logical AND.
// .result=COMPILE_PASS
class Test {
	{
		{
			boolean v;
			if (true && (v=true)) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=false) && true) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=true) && true) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=false) && false) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=true) && false) ;
			boolean b = v;
		}
	}
}
