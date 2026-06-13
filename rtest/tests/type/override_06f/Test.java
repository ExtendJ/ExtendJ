// .result=COMPILE_FAIL
interface I {
	Float m(Object o, byte b);
}

interface J {
	float m(Object o, byte b);
}

interface Test extends I, J {
	// I.m() conflicts with J.m()
}
