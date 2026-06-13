// .result=COMPILE_FAIL
public class Test {
	interface A {
		int a = 4;
		default int m() {
			if(a == 3)
				return 4;
		}
	}
}