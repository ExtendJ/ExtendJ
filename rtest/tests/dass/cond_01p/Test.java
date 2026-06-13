// Test definite assignment checks with logical OR.
// .result=COMPILE_PASS
class Test {
	{
		{
			boolean v;
			if (false || (v=true)) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=false) || true) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=true) || true) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=false) || false) ;
			boolean b = v;
		}

		{
			boolean v;
			if ((v=true) || false) ;
			boolean b = v;
		}
	}
}
