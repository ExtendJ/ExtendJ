// .result=COMPILE_PASS
interface I {
	Float m();
}

interface J {
	Object m();
}

// this is okay because both m are return-type-substitutable
interface Test extends I, J {
}
