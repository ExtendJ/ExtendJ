// Test instantiation of generic class
// .result=COMPILE_PASS
class C<A extends B, B> { }
class Test {
	{
		new C<Integer, Number>();
	}
}
