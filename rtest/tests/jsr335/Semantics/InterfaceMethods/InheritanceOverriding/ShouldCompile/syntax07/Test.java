// .result=COMPILE_PASS

public class Test {
	public interface A {
		static void m() { }
	}
	
	public interface B extends A {
		default void m() { }
	}
	
	public interface C extends B { }
	
	public class D implements C {
		public void someMethod() {
			m();
		}
	}
}