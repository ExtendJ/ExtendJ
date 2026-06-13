// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public <T, S> int meth(T t, S s) {
		return 3;
	}
	
	public void method(int i) {
		A a = this::meth;
	}
}