// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		void m(double i);
	}
	
	public void testMethod() {
		A a = this::m1;
	}
	
	public int m1(float a) {
		return 3;
	}

	public int m1(int i) {
		return 3;
	}
	
	public int m1(long i) {
		return 3;
	}
	
	public int m1(short i) {
		return 3;
	}
	
	public int m1(String i) {
		return 3;
	}
}