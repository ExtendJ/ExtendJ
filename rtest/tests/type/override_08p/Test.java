// .result=COMPILE_PASS
class X<T> {
}

interface I {
	Float m(X<Integer> i, X<Float> f);
}

interface J {
	Object m(X<Integer> i, X<Float> f);
}

interface Test extends I, J {
}
