// .result=COMPILE_PASS


public class Test {
	interface A {
		default void m() {
			System.out.println("Yes");
		}
	}
	
	public class C implements A {
		public void someMethod() {
			m();
		}
	}
}