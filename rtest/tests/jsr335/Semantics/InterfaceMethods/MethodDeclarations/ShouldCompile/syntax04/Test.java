// .result=COMPILE_PASS


public class Test {
	interface A {
		static void m() {
			System.out.println("Yes");
		}
	}
	
	public class C  {
		public void someMethod() {
			A.m();
		}
	}
}