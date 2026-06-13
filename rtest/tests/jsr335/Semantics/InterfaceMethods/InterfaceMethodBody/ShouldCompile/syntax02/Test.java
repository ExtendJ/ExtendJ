// .result=COMPILE_PASS
public class Test {
	interface A {
		int a = 4;
		default void m() {
			int b = this.a;
		}
	}
}