// Enum constructors cannot call super()
// https://bitbucket.org/jastadd/jastaddj/issue/99/super-constructor-access-allowed-in-enum
// .result=COMPILE_FAIL
enum E {
	;
	E() {
		super();
	}
}
