// .result=COMPILE_PASS

public class Test {
	public interface A {
		default void m() { }
	}
	
	public interface B extends A {
		default void m(int i) { }
	}
	
	public interface C extends B { }
	
	public class D implements C {
		public void someMethod() {
			m();
			m(3);
		}
	}
}