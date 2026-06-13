// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(double i, String s);
	}
	
	public class Inner extends Test {
		public int m1(double d, String s) {
			return 3;
		}
		public void testMethod(int i) {
			A a = i==100 ? this::m1 : Inner.super::m1;
		}
	}
	
	public int m1(float d, String s) {
		return 4;
	}
}