// .result=COMPILE_PASS
class Test {

	void foo(String i) {
		switch (i) {
			case "foo":
				break;
			case "foo"+"bar":
				break;
			case "foo"+12:
				break;
			case ""+13:
				break;
		}
	}
}
