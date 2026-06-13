// .result=COMPILE_FAIL


public class Test {
	interface A {
		default <S, T> void m(S i) { }
	}
	interface B {
		default <T, S> void m(S i) { }
	}
	interface C extends A, B { }
}