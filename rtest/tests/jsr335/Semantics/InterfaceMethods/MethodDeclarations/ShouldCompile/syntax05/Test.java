// .result=COMPILE_PASS


public class Test {
	interface A {
		static <S, T> void m(S i) { }
	}
	interface B {
		static <T, S> void m(S i) { }
	}
	interface C extends A, B { }
}