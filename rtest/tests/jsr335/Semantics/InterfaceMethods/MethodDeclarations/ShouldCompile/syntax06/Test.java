// .result=COMPILE_PASS


public class Test {
	interface A {
		strictfp default void m(int i) { }
	}
	interface B {
		default <S extends Integer> int m(S s) {
			return s.intValue();
		}
	}
}