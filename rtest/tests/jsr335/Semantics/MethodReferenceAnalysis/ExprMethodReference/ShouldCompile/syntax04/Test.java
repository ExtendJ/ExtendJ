// .result=COMPILE_PASS

public class Test {
	
	interface A {
		default void m() { }
		double m2();
	}
	
	Integer integ = 5;
	Double doub = 4.9;
	
	public void method(int i) {
		A a = i==4 ? integ::intValue : doub::doubleValue;
	}
}
