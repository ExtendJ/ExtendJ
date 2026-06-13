// .result=COMPILE_PASS
public class Test {
	interface A {
		default void m() { }
	}
	interface B {
		default void m() { }
	}
	
	public class C {
		public void m() { }
	}
	
	public class D extends C implements A, B {
		
	}
}