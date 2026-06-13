// .result=COMPILE_FAIL

public class Test {
	
	interface A<T, S> {
		int m(T t, S s);
	}
	
	public void method(boolean b, A a) {
		
	}
	
	public void testMethod() {
		int i = 3;
		int i2 = 1;
		method(i < i2, (A<Double, Integer>)this::m1);
	}
	
	public int m1(double d, String s) {
		return 4;
	}
}