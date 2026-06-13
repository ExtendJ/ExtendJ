// Enum constructors can not be public
// .result=COMPILE_FAIL
enum E {
	;
	int i;
	public E() {
		i = 3;
	}
}
