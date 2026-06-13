// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a);
	}
	interface B {
		int m(int b);
	}
	
	public void method(int i) {
		A[] as = new A[3];
		as[0] = p -> p + 1;
		as[1] = p -> p + 2;
		as[2] = p -> p + 3;
		B b = as[0]::m;
		B b2 = as[1]::m;
		B b3 = as[2]::m;
	}
}