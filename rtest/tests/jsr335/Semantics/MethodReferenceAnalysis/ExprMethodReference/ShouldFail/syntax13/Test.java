// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public <T, S> S meth(T t, S s) {
		return s;
	}
	
	public void method(int i) {
		A a = this::meth;
	}
}