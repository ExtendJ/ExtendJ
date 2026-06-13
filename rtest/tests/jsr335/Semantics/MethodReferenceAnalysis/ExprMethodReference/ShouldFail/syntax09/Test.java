// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(double i, String s);
	}
	
	public void testMethod() {
		A a = this::<Float, String>m1;
	}
	
	public <S, T> int m1(S s, T t) {
		return 4;
	}
}