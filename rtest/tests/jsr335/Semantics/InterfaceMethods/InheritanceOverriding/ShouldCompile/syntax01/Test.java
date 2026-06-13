// .result=COMPILE_PASS

public class Test {
	public interface A {
		default void m() { }
	}
	
	public interface B extends A { }
	
	public class C implements B {
		public void someMethod() {
			m();
		}
	}
}