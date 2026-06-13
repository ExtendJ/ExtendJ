// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m() {
			return 4;
		}
	}
}