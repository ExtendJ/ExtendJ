// .result=COMPILE_FAIL
public class Test {
	interface A {
		int a = 4;
		static void m() {
			int b = this.a;
		}
	}
}