// .result=COMPILE_PASS
public class Test {
	public interface A {
		static void m() { }
	}
	
	public interface B {
		default void m() { }
	}
	
	public interface C extends A, B { }
	
	public class D implements C {
		public void someMethod() {
			m();
		}
	}
}