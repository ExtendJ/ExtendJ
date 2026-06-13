// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public <T extends Integer, S extends String> int meth(T t, S s) {
		return t.intValue() + s.length();
	}
	
	public void method(int i) {
		A a = this::meth;
	}
}