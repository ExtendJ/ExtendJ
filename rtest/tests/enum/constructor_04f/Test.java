// Enum constructors can not be protected
// .result=COMPILE_FAIL
enum E {
	;
	int i;
	protected E() {
		i = 3;
	}
}
