// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a);
	}
	
	public int locMeth(int b) {
		return b + 5;
	}
	
	public void method(int i) {
		A a = this::locMeth;
	}
}