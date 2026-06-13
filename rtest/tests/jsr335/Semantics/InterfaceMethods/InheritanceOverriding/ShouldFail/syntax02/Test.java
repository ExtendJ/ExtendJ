// A static method may not hide an instance method.
// .result=COMPILE_FAIL
public class Test {
	interface A {
		default <S, T> void m(S i) { }
	}
	
	interface B extends A {
		static <T, S> void m(S i) { }
	}
}
