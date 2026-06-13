// Can not instantiate an enum explicitly
// .result=COMPILE_FAIL
enum E {
	;
	E() {
	}
}

class C {
	E e = new E();
}
