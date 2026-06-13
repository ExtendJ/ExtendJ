// Test generic constructor use
// .result=COMPILE_PASS
class Test<T> {
	{
		new Test<String>();
	}
}
