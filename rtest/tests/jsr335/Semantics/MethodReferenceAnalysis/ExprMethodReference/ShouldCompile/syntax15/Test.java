// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int m(int a, String s);
	}
	
	public class Inner {
		public int method(int a, String s) {
			return a + s.length();
		}
	}
	
	public Inner getInner() {
		return new Inner();
	}
	
	public Test getThis() {
		return this;
	}
	
	public int method(int m, String b) {
		return m + 4 + b.lastIndexOf("s");
	}
	
	public void localMethod(int i) {
		A a = i==10 ? getInner()::method : getThis()::method;
	}
}