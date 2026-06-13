// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public <T, S> T meth(T t, S s) {
		return t;
	}
	
	public void method(int i) {
		A a = this::meth;
	}
}