// Can not instantiate an enum explicitly
// .result=COMPILE_FAIL
enum E {
	;
	E() {
	}

	E e = new E();
}
