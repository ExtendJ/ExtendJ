// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B extends A {
		default void m(int i) { A.super.m(i); }
	}
	
	public class C implements B {
		public void m() {
			m(5);
			A.super.m(4);
		}
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		C c = t.new C();
		c.m();
	}
}