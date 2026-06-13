// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a);
	}
	
	public <T> int meth(T b) {
		return 3;
	}
	
	public void method(int i) {
		A a = this::meth;
	}
}