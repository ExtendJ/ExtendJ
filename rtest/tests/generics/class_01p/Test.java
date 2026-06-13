// Test instantiation of generic class
// .result=COMPILE_PASS
class A<T> extends B<T, A> {
}
class B<T, U extends B> {
}
class Test {
	{
		new A<B>();
	}
}
