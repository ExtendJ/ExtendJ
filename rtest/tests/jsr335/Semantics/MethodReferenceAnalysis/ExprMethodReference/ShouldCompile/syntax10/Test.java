// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a);
	}
	
	public <T, S> int meth(T t) {
		return 3;
	}
	
	public void method(int i) {
		A a = this::meth;
	}
}