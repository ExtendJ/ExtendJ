// Enum declarations can have a constructor
// .result=COMPILE_PASS
enum E {
	;
	int i;
	E() {
		i = 3;
	}
}
